package com.sm.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.constant.TopicConstant;
import com.sm.entity.Image;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class ImageProcessConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ImageProcessConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    /** Its non-blocking retry
     * Retry 2 times and if it fails then it will go to DLQ, DLQ will append '-dead' as a suffix of the current topic
     * @param consumerRecord
     * @throws JsonProcessingException
     */
    @RetryableTopic(attempts = "2",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            backoff = @Backoff(delay = 2000, maxDelay = 10_000, multiplier = 1.5, random = true),
            dltTopicSuffix = "-dead")
    @KafkaListener(topics = TopicConstant.TOPIC_IMAGE_PROCESS, concurrency = "2")
    public void consume(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        var image = objectMapper.readValue(consumerRecord.value(), Image.class);

        // it will throw to global exception
        if (image.getType().equalsIgnoreCase("png")) {
            LOG.warn("Throwing exception on partition {} for image {}", consumerRecord.partition(), image);
            throw new IllegalArgumentException("Simulate API call failed");
        }

        LOG.info("Processing on partition {} for image {}", consumerRecord.partition(), image);
    }

//	@KafkaListener(topics = "t-image-process", containerFactory = "imageRetryContainerFactory", concurrency = "2")
//	public void consume(ConsumerRecord<String, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
//		var image = objectMapper.readValue(consumerRecord.value(), Image.class);
//
//		if (image.getType().equalsIgnoreCase("svg")) {
//			LOG.warn("Throwing exception on partition {} for image {}", consumerRecord.partition(), image);
//			throw new IllegalArgumentException("Simulate API call failed");
//		}
//
//		LOG.info("Processing on partition {} for image {}", consumerRecord.partition(), image);
//	}


}
