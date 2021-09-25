package org.rohan.queue.entity;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class StringMessage implements Message {
    private final String msg;

    @Override
    public String toReadableString() {
        return String.format(msg);
    }
}
