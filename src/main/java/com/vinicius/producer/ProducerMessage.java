package com.vinicius.producer;

import static com.vinicius.config.KafkaConfig.getInstance;

import java.util.List;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vinicius.mapper.DataMapper;

public class ProducerMessage<T> {

    private static final Logger logger = LoggerFactory.getLogger(ProducerMessage.class);

    private final KafkaProducer<String, byte[]> kafkaProducer;
    private final DataMapper<T> dataMapper;
    private final String topic;

    public ProducerMessage(DataMapper<T> dataMapper, String topic) {
	this.dataMapper = dataMapper;
	this.topic = topic;
	kafkaProducer = new KafkaProducer<>(getInstance().createProducerProperties());
	kafkaProducer.initTransactions();
    }

    public void sendMessage(T message) {
	kafkaProducer.beginTransaction();

	try {
	    publishMessage(message);
	    kafkaProducer.commitTransaction();
	} catch (Exception e) {
	    logger.error("Error to send message", e);
	    kafkaProducer.abortTransaction();
	} finally {
	    //kafkaProducer.close();
	}
    }

    public void sendMessages(List<T> messages) {
	//kafkaProducer.initTransactions();
	kafkaProducer.beginTransaction();

	try {
	    messages.forEach(this::publishMessage);
	    kafkaProducer.commitTransaction();
	} catch (Exception e) {
	    logger.error("Error to send message", e);
	    kafkaProducer.abortTransaction();
	} finally {
	   // kafkaProducer.close();
	}
    }

    private void publishMessage(T message) {
	ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, dataMapper.from(message));
	kafkaProducer.send(record);

	// Is it needed?
	kafkaProducer.flush();
    }
}
