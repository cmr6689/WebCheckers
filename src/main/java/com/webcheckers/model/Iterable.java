package com.webcheckers.model;

import java.util.Iterator;

/**
 * The board iterator
 * @param <T> iterator
 */
public interface Iterable<T> {
    /**
     * Create the iterator
     * @return null
     */
    public Iterator<T> iterator();
}
