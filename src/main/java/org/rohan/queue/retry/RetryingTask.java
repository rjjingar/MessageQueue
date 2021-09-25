package org.rohan.queue.retry;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class RetryingTask {

    private final Runnable runnable;
    private final RetryPolicy retryPolicy;

    public boolean run() {
        int attempt = 1;
        boolean continueRetry = true;
        while (continueRetry) {
            try {
                runnable.run();
                return true;
            } catch (Exception e) {
                System.out.println("#################### Retry Attempt #" + attempt);
                continueRetry = retryPolicy.attemptRetry(attempt, e);
                attempt++;
            }
        }
        return false;
    }
}
