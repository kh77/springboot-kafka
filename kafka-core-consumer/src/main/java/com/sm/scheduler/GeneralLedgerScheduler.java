package com.sm.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class GeneralLedgerScheduler {

	@Autowired
	private KafkaListenerEndpointRegistry registry;
	
	private static final Logger LOG = LoggerFactory.getLogger(GeneralLedgerScheduler.class);

	//stop consumer cron time

	@Scheduled(cron = "0 21 15 1/1 * ?")
	public void stop() {
		LOG.info("Stopping consumer");
		registry.getListenerContainer("general-ledger.one").pause();
	}

	//start consumer cron time

	@Scheduled(cron = "0 25 15 1/1 * ?")
	public void start() {
		LOG.info("Starting consumer");
		registry.getListenerContainer("general-ledger.one").resume();
	}

}
