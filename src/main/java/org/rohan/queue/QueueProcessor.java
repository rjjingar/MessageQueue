package org.rohan.queue;

import lombok.RequiredArgsConstructor;
import org.rohan.queue.entity.Topic;
import org.rohan.queue.storage.InMemoryMessageQueue;
import org.rohan.queue.storage.InMemoryTopicRegistry;
import org.rohan.queue.storage.MessageQueue;

@RequiredArgsConstructor
public class QueueProcessor implements Runnable {

    final Topic topic;

    @Override
    public void run() {
        MessageQueue queue = InMemoryTopicRegistry.getInstance().getMessageQueue(topic);
        while (true) {
            queue.flush();
        }
    }
}
