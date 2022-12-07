package com.sm.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.entity.CarLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CarLocationConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CarLocationConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * it will accept all message
     * @param message
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = "t-location", groupId = "cg-all-location")
    public void consumeCarLocation(String message) throws JsonProcessingException {
        var carLocation = objectMapper.readValue(message, CarLocation.class);
        LOG.info("Car Location : {}", carLocation);
    }

    /**
     * Add custom Container Factory and add filter to match the condition and filter out the message in this consumer
     * Only accept and show message which have distance more than 100
     * @param message
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = "t-location", groupId = "cg-far-location", containerFactory = "farLocationContainerFactory")
    public void listenFar(String message) throws JsonProcessingException {
        var carLocation = objectMapper.readValue(message, CarLocation.class);
        LOG.info("Car Location Only Accept Greater Than 100 Distance: {}", carLocation);
    }
}
