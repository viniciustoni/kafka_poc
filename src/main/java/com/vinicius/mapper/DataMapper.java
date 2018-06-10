package com.vinicius.mapper;

public interface DataMapper<T> {

    T to(byte[] value);

    byte[] from(T data);

}
