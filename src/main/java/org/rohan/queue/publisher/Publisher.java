package org.rohan.queue.publisher;

import org.rohan.queue.entity.Message;
import org.rohan.queue.entity.Topic;

public interface Publisher {
    void publish(Topic topic, Message msg);

    String getName();
}
