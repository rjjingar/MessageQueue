package org.rohan.queue.entity;

public class QueueFullException extends RuntimeException {

    public QueueFullException(String msg) {
        super(msg);
    }

}
