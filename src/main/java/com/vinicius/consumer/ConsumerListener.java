package com.vinicius.consumer;

import static com.vinicius.config.KafkaConfig.getInstance;
import static java.util.Arrays.asList;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vinicius.mapper.DataMapper;
import com.vinicius.processor.MessageProcessor;

public class ConsumerListener<T> implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerListener.class);

    private final AtomicBoolean close = new AtomicBoolean(false);
    private final String topic;
    private final KafkaConsumer<String, byte[]> kafkaConsumer;
    private final DataMapper<T> dataMapper;
    private final MessageProcessor<T> messageProcessor;

    public ConsumerListener(String topic, DataMapper<T> dataMapper, MessageProcessor<T> messageProcessor) {
	this.topic = topic;
	this.dataMapper = dataMapper;
	this.messageProcessor = messageProcessor;
	this.kafkaConsumer = new KafkaConsumer<>(getInstance().createConsumerProperties());
	this.kafkaConsumer.subscribe(asList(topic));
    }

    @Override
    public void run() {
	startListener();
    }

    public void shutdown() {
	close.set(true);
	kafkaConsumer.close();
    }

    public void startListener() {
	logger.info("Start to listening on topic [{}]", topic);
	while (!close.get()) {
	    ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(100);
	    records.forEach(this::processRecord);
	}
    }

    private void processRecord(final ConsumerRecord<String, byte[]> consumerRecord) {
	T object = dataMapper.to(consumerRecord.value());
	messageProcessor.processMessage(object);
    }
}
