package com.bm.springkafka;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @author Binay Mishra
 *
 * Step-1 C:\apache-zookeeper-3.7.0\bin>zkServer
 * Step-2 C:\kafka_2.12-2.8.0>.\bin\windows\kafka-server-start.bat .\config\server.properties
 * Step-3 kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@KafkaListener(topics = "topic-1", groupId = "group_id")
	public void listenGreeting(String message) {
		System.out.println("Consume message << " + message);
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(senderProps());
	}

	private Map<String, Object> senderProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		//...
		return props;
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

	@Bean
	ApplicationRunner applicationRunner(final KafkaTemplate<String, String> kafkaTemplate) {
		return args ->
			IntStream.range(0, 10)
					.forEach(i -> {
						String message =  i + ". Hi, Binay !";
						System.out.println("Produce message >> " + message);
						kafkaTemplate.send("topic-1", message);
					});
	}
}
