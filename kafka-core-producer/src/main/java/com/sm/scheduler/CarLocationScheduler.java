package com.sm.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sm.entity.CarLocation;
import com.sm.producer.CarLocationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CarLocationScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(CarLocationScheduler.class);

    @Autowired
    private CarLocationProducer producer;

    @Scheduled(fixedRate = 3000000)
    public void generateCarLocation() throws JsonProcessingException {
        var now = System.currentTimeMillis();
        CarLocation carOne = new CarLocation("car-one", now, 0);
        CarLocation carTwo = new CarLocation("car-two", now, 110);
        CarLocation carThree = new CarLocation("car-three", now, 95);

        producer.send(carOne);
        producer.send(carTwo);
        producer.send(carThree);
    }

}
