package org.rohan.queue.storage;

import org.rohan.queue.entity.Message;
import org.rohan.queue.subscriber.Subscriber;

public interface MessageQueue {

    void publish(Message msg);

    void flush();

    void subscribe(Subscriber subscriber);

    void unsubscribe(Subscriber subscriber);
}
