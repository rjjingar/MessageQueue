package org.rohan.queue.publisher;

import org.rohan.queue.storage.MessageQueue;
import org.rohan.queue.entity.InvalidQueueException;
import org.rohan.queue.entity.Message;
import org.rohan.queue.entity.Topic;
import org.rohan.queue.storage.InMemoryTopicRegistry;
import org.rohan.queue.retry.RetryPolicy;
import org.rohan.queue.retry.RetryingTask;

import java.util.UUID;

public class SimplePublisher implements Publisher {

    String id;
    String name;
    RetryPolicy retryPolicy;

    public SimplePublisher(String name) {
        this.id =UUID.randomUUID().toString();
        this.name = name;
        retryPolicy = RetryPolicy.builder().build();
    }

    @Override
    public void publish(Topic topic, Message msg) {
        MessageQueue queue = InMemoryTopicRegistry.getInstance().getMessageQueue(topic);
        if (queue == null) throw new InvalidQueueException("Given topic does not exist: " + topic.getName());
        boolean successful = new RetryingTask(() -> queue.publish(msg), retryPolicy).run();
        if (!successful) {
            System.out.println("Publishing failed for message " + msg.toReadableString());
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
