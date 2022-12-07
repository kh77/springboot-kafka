package com.sm.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sm.entity.MagicNumber;
import com.sm.producer.MagicNumberProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

//@Component
public class MagicNumberScheduler {

    @Autowired
    private MagicNumberProducer producer;

    @Scheduled(fixedRate = 3000000)
    public void generate() throws JsonProcessingException {
        producer.send(new MagicNumber(ThreadLocalRandom.current().nextInt(1, 1000)));
    }

}
