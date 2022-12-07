package com.sm.consumer;

import com.sm.constant.TopicConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GeneralLedgerConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralLedgerConsumer.class);

    /*
        there is id field ,
        from scheduler GeneralLedgerScheduler, we reference this id to manually start and stop this consumer
        then this consumer will stop and the next consumer will retrieve the message
     */
    @KafkaListener(topics = TopicConstant.TOPIC_GENERAL_LEDGER, id = "general-ledger.one")
    public void consumeOne(String message) {
        LOG.info("From GeneralLedgerConsumer One : {}", message);
    }

    @KafkaListener(topics = TopicConstant.TOPIC_GENERAL_LEDGER)
    public void consumeTwo(String message) {
        LOG.info("From GeneralLedgerConsumer Two : {}", message);
    }

}
