package com.sm.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

import static com.sm.constant.TopicConstant.TOPIC_GENERAL_LEDGER;

@Component
public class GeneralLedgerProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private AtomicInteger counter = new AtomicInteger();

    public void send(String message) {
        kafkaTemplate.send(TOPIC_GENERAL_LEDGER, message);
    }

    @Scheduled(fixedRate = 30000000)
    public void schedule() {
        var message = "Ledger " + counter.incrementAndGet();
        send(message);
    }

}
