package com.bm.springkafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service("springKafkaProducer")
public class SpringKafkaProducer {

    private static final String TOPIC = "topic_local";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SpringKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        if(StringUtils.hasText(message)) {
            log.info("Produced >> {}", message);
            kafkaTemplate.send(TOPIC, message);
        } else {
            log.error("Invalid message {}", message);
        }
    }
}
