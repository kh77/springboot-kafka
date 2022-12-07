package com.sm.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.constant.TopicConstant;
import com.sm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.sm.constant.TopicConstant.*;

@Service
public class OrderProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void send(Order order) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(order);
        kafkaTemplate.send(TOPIC_ORDER, json);
    }

}
