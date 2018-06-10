package com.vinicius.config;

import static com.vinicius.constants.ApplicationConstants.ACK_VALUE;
import static com.vinicius.constants.ApplicationConstants.CLIENT_ID;
import static com.vinicius.constants.ApplicationConstants.KAFKA_SERVER;
import static com.vinicius.constants.ApplicationConstants.TRANSACTION_ID_VALUE;
import static com.vinicius.constants.ApplicationConstants.ZOOKEPER_SERVER;
import static kafka.admin.AdminUtils.createTopic;
import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ISOLATION_LEVEL_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.TRANSACTIONAL_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static scala.collection.JavaConverters.seqAsJavaList;

import java.util.List;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import kafka.admin.RackAwareMode;
import kafka.cluster.Broker;
import kafka.utils.ZkUtils;

public final class KafkaConfig {

    private static final KafkaConfig _SINGLETON = new KafkaConfig();

    private KafkaConfig() {
    }

    public static KafkaConfig getInstance() {
	return _SINGLETON;
    }

    public Properties createProducerProperties() {
	Properties properties = createProperties();

	// for transactions
	properties.put(ACKS_CONFIG, ACK_VALUE);
	properties.put(TRANSACTIONAL_ID_CONFIG, TRANSACTION_ID_VALUE);

	properties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	properties.put(VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

	return properties;
    }

    public Properties createConsumerProperties() {
	Properties properties = createProperties();

	// for transactions
	properties.put(ISOLATION_LEVEL_CONFIG, "read_committed");

	properties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

	return properties;
    }

    private Properties createProperties() {
	final Properties properties = new Properties();

	properties.put(BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
	properties.put(CLIENT_ID_CONFIG, CLIENT_ID);
	properties.put("group.id", CLIENT_ID);

	return properties;
    }

    public void createNewTopic(final String topicName, final int partitions, final int replicationFactor) {
	final ZkClient zkClient = new ZkClient(ZOOKEPER_SERVER);
	final ZkUtils zkUtils = ZkUtils.apply(zkClient, false);

	createTopic(zkUtils, topicName, partitions, replicationFactor, new Properties(),
		RackAwareMode.Enforced$.MODULE$);
    }

    private String getBrokenList() {

	final StringBuilder serverList = new StringBuilder();

	final ZkClient zkClient = new ZkClient(ZOOKEPER_SERVER);
	final ZkUtils zkUtils = ZkUtils.apply(zkClient, true);

	final List<Broker> brokers = seqAsJavaList(zkUtils.getAllBrokersInCluster());

	for (Broker broker : brokers) {
	    seqAsJavaList(broker.endPoints())
		    .forEach(endPoint -> serverList.append(endPoint.connectionString()).toString());
	}

	return serverList.toString();
    }
}
