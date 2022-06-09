package com.bm.springkafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootApplication
public class SpringKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(SpringKafkaProducer kafkaProducer) {
		return args -> IntStream
				.range(0, 10)
				.forEach(i -> kafkaProducer.send(i + ". " + UUID.randomUUID()));
	}
}
