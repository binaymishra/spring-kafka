package com.bm.springkafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @author Binay Mishra
 */
@EnableKafka
@Configuration
public class KafkaConfig {

  @Autowired
  private KafkaProperties kafkaProperties;

  @Bean
  public ProducerFactory<String, Object> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerProps());
  }

  private Map<String, Object> producerProps() {
    Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    // See https://kafka.apache.org/documentation/#producerconfigs for more properties
    return props;
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }


  private Map<String, Object> consumerProps() {
    Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Object.class);
    // See https://kafka.apache.org/documentation/#consumerconfigs for more properties
    return props;
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
    jsonDeserializer.addTrustedPackages("*");
    return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(),
        jsonDeserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }


}
