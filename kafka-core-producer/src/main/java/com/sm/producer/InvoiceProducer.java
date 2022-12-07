package com.sm.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.entity.Image;
import com.sm.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.sm.constant.TopicConstant.TOPIC_IMAGE_PROCESS;
import static com.sm.constant.TopicConstant.TOPIC_INVOICE;

@Service
public class InvoiceProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * There are 2 partitions , sending image to specific partition
     * @param invoice
     * @throws JsonProcessingException
     */
    public void send(Invoice invoice) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(invoice);
        kafkaTemplate.send(TOPIC_INVOICE, json);
    }

}
