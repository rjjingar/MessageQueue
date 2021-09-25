package org.rohan.queue.storage;

import org.rohan.queue.entity.Topic;

public interface TopicRegistry {

    void addTopic(Topic topic);
    void addTopic(Topic topic, MessageQueue queue);

    void removeTopic(Topic topic);

    MessageQueue getMessageQueue(Topic topic);

}
