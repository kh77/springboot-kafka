package com.sm.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sm.entity.Invoice;
import com.sm.producer.ImageProcessProducer;
import com.sm.producer.InvoiceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InvoiceScheduler {
    @Autowired
    private InvoiceProducer producer;
    private static AtomicInteger counter = new AtomicInteger();

    @Scheduled(fixedRate = 10000)
    public void generate() throws JsonProcessingException {
        var invoiceNumber = "INV-" + counter.incrementAndGet();
        var amount = ThreadLocalRandom.current().nextInt(1, 1000);
        var invoice = new Invoice(invoiceNumber, amount, "USD");
        producer.send(invoice);
    }
}
