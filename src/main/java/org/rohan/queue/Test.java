package org.rohan.queue;

import org.rohan.queue.entity.InMemoryTopic;
import org.rohan.queue.entity.StringMessage;
import org.rohan.queue.entity.Topic;
import org.rohan.queue.publisher.Publisher;
import org.rohan.queue.publisher.SimplePublisher;
import org.rohan.queue.storage.InMemoryTopicRegistry;
import org.rohan.queue.storage.MessageQueue;
import org.rohan.queue.storage.TopicRegistry;
import org.rohan.queue.subscriber.SimpleSubscriber;
import org.rohan.queue.subscriber.Subscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static Topic TOPIC_1 = new InMemoryTopic("topic-1");
    public static Topic TOPIC_2 = new InMemoryTopic("topic-2");

    public static void main(String[] args) {
        TopicRegistry REGISTRY = InMemoryTopicRegistry.getInstance();

        REGISTRY.addTopic(TOPIC_1);
        REGISTRY.addTopic(TOPIC_2);

        PublishProcessor PUBLISHER_1 = new PublishProcessor(new SimplePublisher("publisher-A"), TOPIC_1);
        PublishProcessor PUBLISHER_2 = new PublishProcessor(new SimplePublisher("publisher-B"), TOPIC_2);

        ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

        Subscriber subscriber1 = new SimpleSubscriber("1");
        Subscriber subscriber2 = new SimpleSubscriber("2");
        Subscriber subscriber3 = new SimpleSubscriber("3");
        Subscriber subscriber4 = new SimpleSubscriber("4");

        MessageQueue queue1 = REGISTRY.getMessageQueue(TOPIC_1);
        MessageQueue queue2 = REGISTRY.getMessageQueue(TOPIC_2);

        queue1.subscribe(subscriber1);
        queue1.subscribe(subscriber2);
        queue1.subscribe(subscriber3);

        queue2.subscribe(subscriber3);
        queue2.subscribe(subscriber4);
        EXECUTOR_SERVICE.submit(() -> queue1.flush());
        //EXECUTOR_SERVICE.submit(() -> queue2.flush());
        EXECUTOR_SERVICE.submit(() -> PUBLISHER_1.run());
        //EXECUTOR_SERVICE.submit(() -> PUBLISHER_2.run());


    }
}
