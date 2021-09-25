package org.rohan.queue.entity;

import java.util.UUID;

public class InMemoryTopic implements Topic {

    String name;
    String id;

    public InMemoryTopic(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }
}
