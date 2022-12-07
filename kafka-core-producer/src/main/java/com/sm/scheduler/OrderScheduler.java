package com.sm.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sm.entity.Order;
import com.sm.producer.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

//@Component
public class OrderScheduler {

    @Autowired
    private OrderProducer producer;

    @Scheduled(fixedRate = 3000000)
    public void generate() throws JsonProcessingException {
        var number = ThreadLocalRandom.current().nextInt(1, 10);
        producer.send(new Order(number, "Order-" + number));
    }

}
