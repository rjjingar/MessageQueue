package org.rohan.queue.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Pair<T,V> {
    @Getter
    private final T key;
    @Getter
    private final V value;

}
