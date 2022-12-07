package com.sm.error.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class GlobalErrorHandler implements CommonErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        LOG.warn("GLOBAL ERROR HANDLER ---> handleOtherException");
        LOG.warn("Handling other exception : {}", thrownException.getMessage());
    }

    @Override
    public void handleRecord(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        LOG.warn("GLOBAL ERROR HANDLER ---> handleRecord");
        LOG.warn("Handling exception for record : {}, because : {}", record.value(), thrownException.getMessage());
    }

}
