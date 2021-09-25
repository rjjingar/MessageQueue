package org.rohan.queue.storage;

import org.rohan.queue.entity.Message;
import org.rohan.queue.entity.Pair;
import org.rohan.queue.entity.PoisonMessage;
import org.rohan.queue.entity.QueueFullException;
import org.rohan.queue.subscriber.Subscriber;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class InMemoryMessageQueue implements MessageQueue {
    public static final int MAX_QUEUE_SIZE = 10;
    public static final int PUBLISH_TIMEOUT = 200;
    private ArrayBlockingQueue<Message> queue;
    private Queue<Pair<Message, Subscriber>> deadLetterQueue;
    private final Set<Subscriber> subscribers;

    public InMemoryMessageQueue() {
        queue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
        deadLetterQueue = new ArrayDeque<>(MAX_QUEUE_SIZE);
        subscribers = new HashSet<>();
    }

    public void printStats(String id) {
        System.out.println(id + " queue size " + queue.size() + ", DLQ " + deadLetterQueue.size() + " subs " + subscribers.size());
    }

    @Override
    public void publish(Message msg) {
        try {
            queue.offer(msg, 10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("################ Queue is full!!!!!!!!!!!!!!");
            throw new QueueFullException("Queue if full. Retry in some time!");
        }
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void flush() {
        while (true) {
            Message msg = null;
            try {
                Thread.sleep(500);
                msg = queue.take();
                if (msg instanceof PoisonMessage) {
                    System.out.println("Poison message found. Stopping processor");
                    return;
                }
                flushMessage(msg);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void retryDeadLetterQueue() {
        while (!deadLetterQueue.isEmpty()) {
            Pair<Message, Subscriber> dl = deadLetterQueue.poll();
            try {
                dl.getValue().callBack().accept(dl.getKey());
            } catch (Exception e) {
                deadLetterQueue.offer(dl);
                throw new QueueFullException("DL retry failed!");
            }
        }
    }

    private void flushMessage(Message msg) {
        for (Subscriber s : subscribers) {
            try {
                s.callBack().accept(msg);
            } catch (Exception e) {
                deadLetterQueue.offer(new Pair<>(msg, s));
            }
        }
    }
}
