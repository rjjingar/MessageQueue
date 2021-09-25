package org.rohan.queue.storage;

import org.rohan.queue.entity.Pair;
import org.rohan.queue.entity.Topic;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTopicRegistry implements TopicRegistry {

    private static InMemoryTopicRegistry REGISTRY;

    public static TopicRegistry getInstance() {
        if (REGISTRY == null) {
            REGISTRY = new InMemoryTopicRegistry();
        }
        return REGISTRY;
    }

    private ConcurrentHashMap<String, Pair<Topic, MessageQueue>> registryMap;

    private InMemoryTopicRegistry() {
        registryMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addTopic(Topic topic) {
        addTopic(topic, new InMemoryMessageQueue());
    }

    @Override
    public void addTopic(Topic topic, MessageQueue queue) {
        if (!registryMap.containsKey(topic.getId())) {
            registryMap.put(topic.getId(), new Pair<>(topic, queue));
        }
    }

    @Override
    public void removeTopic(Topic topic) {
        registryMap.remove(topic.getId());
    }

    @Override
    public MessageQueue getMessageQueue(Topic topic) {
        return registryMap.get(topic.getId()).getValue();
    }
}
