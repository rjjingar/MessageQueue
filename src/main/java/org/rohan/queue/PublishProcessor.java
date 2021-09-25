package org.rohan.queue;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.rohan.queue.entity.Message;
import org.rohan.queue.entity.StringMessage;
import org.rohan.queue.entity.Topic;
import org.rohan.queue.publisher.Publisher;
import org.rohan.queue.publisher.SimplePublisher;
import org.rohan.queue.storage.InMemoryMessageQueue;
import org.rohan.queue.storage.InMemoryTopicRegistry;
import org.rohan.queue.storage.MessageQueue;

import java.util.Random;

import static org.rohan.queue.storage.InMemoryMessageQueue.MAX_QUEUE_SIZE;

@RequiredArgsConstructor
public class PublishProcessor implements Runnable {

    private final Publisher p;
    private final Topic t;

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        int iter = 0;
        String name = "[" + p.getName() + "]";
        InMemoryMessageQueue queue = (InMemoryMessageQueue) InMemoryTopicRegistry.getInstance().getMessageQueue(t);

        while (true) {
            iter++;
            queue.printStats(p.getName());
            for (int i = 0; i < MAX_QUEUE_SIZE; i++) {
                Message msg = new StringMessage(name + " Batch-" + iter + ": Message#" + i);
                p.publish(t, msg);
                Thread.sleep(10);
            }
        }
    }
}
