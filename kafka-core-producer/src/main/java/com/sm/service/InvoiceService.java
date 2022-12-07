package com.sm.service;

import com.sm.entity.Invoice;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InvoiceService {

    private AtomicInteger counter = new AtomicInteger();

    public Invoice generateInvoice() {
        var invoiceNumber = "INV-" + counter.incrementAndGet();
        var amount = ThreadLocalRandom.current().nextInt(1, 1000);
        return new Invoice(invoiceNumber, amount, "USD");
    }

}
