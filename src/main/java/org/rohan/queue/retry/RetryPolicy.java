package org.rohan.queue.retry;

import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Builder
public class RetryPolicy {
    @Builder.Default
    int maxRetries = 1;
    @Builder.Default
    int retryInterval = 100;
    @Builder.Default
    Random random = new Random();
    @Builder.Default
    List<Exception> retryOn = Collections.emptyList();

    public boolean attemptRetry(int attempt, Exception e) {
        if (shouldRetry(attempt, e)) {
            int time = (int) Math.pow(2, attempt) * retryInterval + random.nextInt(retryInterval);
            try {
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                return false;
            }
            return true;
        }
        return false;
    }
    private boolean shouldRetry(int attempt, Exception e) {
        if (attempt < maxRetries) {
            return (retryOn == null || retryOn.isEmpty()) ? true : retryOn.contains(e);
        }
        return false;
    }
}
