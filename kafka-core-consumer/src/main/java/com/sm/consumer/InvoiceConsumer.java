package com.sm.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.sm.entity.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class InvoiceConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(InvoiceConsumer.class);
	
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * will retry 3 times if it is failed then send to t-invoice-dead topic
	 * @param message
	 * @throws JsonProcessingException
	 */
	@KafkaListener(topics = "t-invoice", concurrency = "2", containerFactory = "invoiceDltContainerFactory")
	public void consume(String message) throws JsonProcessingException {
		var invoice = objectMapper.readValue(message, Invoice.class);
		if (invoice.getAmount() < 100000) {
			throw new IllegalArgumentException("Invalid amount for " + invoice);
		}
		
		LOG.info("Consumer invoice : {}", invoice);
	}
	
}
