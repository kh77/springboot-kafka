package com.sm.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sm.entity.Image;
import com.sm.producer.ImageProcessProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ImageProcessScheduler {
    @Autowired
    private ImageProcessProducer producer;
    private static AtomicInteger counter = new AtomicInteger();
    private String imageExtension = "png:gif";

    @Scheduled(fixedRate = 30000000)
    public void generate() throws JsonProcessingException {
        int value = counter.incrementAndGet();
        String[] wordsSplit = imageExtension.split(":");
        var name = "image-" + value;
        var reminder = value % 2;
        var size = ThreadLocalRandom.current().nextLong(100, 10_000);
        Image image = new Image(name, size, wordsSplit[reminder]);
        producer.send(image, reminder);
    }

}
