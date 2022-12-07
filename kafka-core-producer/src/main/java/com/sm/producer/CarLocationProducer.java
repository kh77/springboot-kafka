package com.sm.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sm.entity.CarLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.sm.constant.TopicConstant.TOPIC_LOCATION;

@Service
public class CarLocationProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	
	public void send(CarLocation carLocation) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(carLocation);
		kafkaTemplate.send(TOPIC_LOCATION, json);
	}
	
}
