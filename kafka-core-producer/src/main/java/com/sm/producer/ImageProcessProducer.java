package com.sm.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.sm.constant.TopicConstant.TOPIC_IMAGE_PROCESS;

@Service
public class ImageProcessProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * There are 2 partitions , sending image to specific partition
     * @param image
     * @param partition
     * @throws JsonProcessingException
     */
    public void send(Image image, int partition) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(image);
        kafkaTemplate.send(TOPIC_IMAGE_PROCESS, partition, image.getType(), json);
    }

}
