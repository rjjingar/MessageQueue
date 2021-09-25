package org.rohan.queue.entity;

public class PoisonMessage implements Message {

    @Override
    public String toReadableString() {
        return "Poison Message";
    }
}
