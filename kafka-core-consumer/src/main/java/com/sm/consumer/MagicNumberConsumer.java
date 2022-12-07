package com.sm.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.constant.TopicConstant;
import com.sm.entity.MagicNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MagicNumberConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(MagicNumberConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = TopicConstant.TOPIC_MAGIC_NUMBER)
    public void consume(String message) throws JsonProcessingException {
        var magicNumber = objectMapper.readValue(message, MagicNumber.class);
        LOG.info("Consume Magic Number : {}", magicNumber);
    }
}
