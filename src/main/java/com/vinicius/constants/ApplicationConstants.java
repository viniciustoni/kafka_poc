package com.vinicius.constants;

/**
 * TODO: Change from Constants to Properties.
 * 
 * @author vinicius
 *
 */
public abstract class ApplicationConstants {

    public static final String KAFKA_SERVER = "localhost:9092,localhost:9093,localhost:9094";
    public static final String ZOOKEPER_SERVER = "localhost:2181";
    public static final String TOPIC_NAME = "logUser";
    public static final String CLIENT_ID = "logUser";
    public static final String ACK_VALUE = "all";
    public static final String TRANSACTION_ID_VALUE = "test_transaction_1";

    private ApplicationConstants() {
    }
}
