package org.rohan.queue.entity;

public class InvalidQueueException extends RuntimeException {

    public InvalidQueueException(String msg) {
        super(msg);
    }
}
