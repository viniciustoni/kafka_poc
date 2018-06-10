package com.vinicius;

import static com.vinicius.config.KafkaConfig.getInstance;

import com.vinicius.constants.ApplicationConstants;

public class TopicCreateApplication {
    public static void main(String... args) {
	getInstance().createNewTopic(ApplicationConstants.TOPIC_NAME, 3, 3);
    }
}
