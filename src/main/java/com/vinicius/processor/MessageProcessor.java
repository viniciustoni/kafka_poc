package com.vinicius.processor;

public interface MessageProcessor<T> {

    void processMessage(T object);

}
