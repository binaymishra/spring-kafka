package com.bm.springkafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service("springKafkaConsumer")
public class SpringKafkaConsumer {

    private static final String TOPIC = "topic_local";

    @KafkaListener(topics = {TOPIC}, groupId = "group_id")
    public void consume(String message) {
        log.info("Consumed << {}", message);
    }
}
