package com.sm.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.constant.TopicConstant;
import com.sm.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    /*
        Using OrderErrorHandler to handle any kind of exception in this consumer and
        GlobalErrorHandler will also be fired
     */
    @KafkaListener(topics = TopicConstant.TOPIC_ORDER, errorHandler = "orderErrorHandler")
    public void consume(String message) throws JsonProcessingException {
        var order = objectMapper.readValue(message, Order.class);

        if (order.getAmount() > 5) {
            throw new IllegalArgumentException("Order amount is high: " + order.getAmount());
        }

        LOG.info("Consume Order : {}", order);
    }

}
