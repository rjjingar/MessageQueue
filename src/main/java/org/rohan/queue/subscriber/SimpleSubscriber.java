package org.rohan.queue.subscriber;

import org.rohan.queue.entity.Message;

import java.util.function.Consumer;

public class SimpleSubscriber implements Subscriber {

    String id;
    public SimpleSubscriber(String id) {
        this.id = id;
    }

    @Override
    public Consumer<Message> callBack() {
        return (msg) -> System.out.println(String.format("[Consumer:%s received]->%s", id, msg.toReadableString()));
    }
}
