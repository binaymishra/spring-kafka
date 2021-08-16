package com.bm.springkafka;

import java.util.stream.IntStream;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

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
