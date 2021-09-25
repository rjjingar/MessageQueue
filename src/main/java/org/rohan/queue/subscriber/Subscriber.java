package org.rohan.queue.subscriber;

import org.rohan.queue.entity.Message;

import java.util.function.Consumer;

public interface Subscriber {
    Consumer<Message> callBack();
}
