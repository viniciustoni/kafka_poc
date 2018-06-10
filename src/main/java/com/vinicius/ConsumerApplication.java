package com.vinicius;

import static com.vinicius.constants.ApplicationConstants.TOPIC_NAME;

import com.vinicius.consumer.ConsumerListener;
import com.vinicius.mapper.entity.LogUserDataMapper;
import com.vinicius.processor.impl.LogUserProcessor;

public class ConsumerApplication {
    public static void main(String... args) {
	new ConsumerListener<>(TOPIC_NAME, new LogUserDataMapper(), new LogUserProcessor()).startListener();
    }
}
