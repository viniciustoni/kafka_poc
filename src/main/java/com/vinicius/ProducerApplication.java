package com.vinicius;

import static com.vinicius.constants.ApplicationConstants.TOPIC_NAME;
import static java.lang.Long.valueOf;
import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.vinicius.entity.LogUser;
import com.vinicius.mapper.entity.LogUserDataMapper;
import com.vinicius.producer.ProducerMessage;

public class ProducerApplication {

    private static final String USER_NAME_TEMPLATE = "Test {0}";

    public static void main(String... args) {
	//sendMessageOneByOne();
	sendMessageBatch();
    }

    private static void sendMessageOneByOne() {

	ProducerMessage<LogUser> producerMessage = new ProducerMessage<>(new LogUserDataMapper(), TOPIC_NAME);
	for (int i = 0; i < 1000; i++) {
	    LogUser logUser = new LogUser(valueOf(i), format(USER_NAME_TEMPLATE, i),
		    GregorianCalendar.getInstance().getTime());
	    producerMessage.sendMessage(logUser);
	}
    }

    private static void sendMessageBatch() {

	ProducerMessage<LogUser> producerMessage = new ProducerMessage<>(new LogUserDataMapper(), TOPIC_NAME);
	List<LogUser> logUsers = new ArrayList<>();

	for (int i = 0; i < 1000; i++) {
	    logUsers.add(
		    new LogUser(valueOf(i), format(USER_NAME_TEMPLATE, i), GregorianCalendar.getInstance().getTime()));
	}

	producerMessage.sendMessages(logUsers);
    }

}
