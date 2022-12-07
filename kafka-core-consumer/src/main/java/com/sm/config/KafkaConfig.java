package com.sm.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.entity.CarLocation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        var properties = kafkaProperties.buildConsumerProperties();

        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000");

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean(name = "farLocationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory());

        factory.setRecordFilterStrategy(consumerRecord -> {
            try {
                CarLocation carLocation = objectMapper.readValue(consumerRecord.value().toString(), CarLocation.class);
                return carLocation.getDistance() <= 100;
            } catch (JsonProcessingException e) {
                return false;
            }
        });

        return factory;
    }

//	@Bean(name = "kafkaListenerContainerFactory")
//	public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
//			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
//		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
//		configurer.configure(factory, consumerFactory());
//
//		factory.setCommonErrorHandler(new GlobalErrorHandler());
//
//		return factory;
//	}

    /**
     * Blocking Retry
     * @param configurer
     * @return
     */

    @Bean(name = "imageRetryContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> imageRetryContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {

        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory());


        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(10_000, 3)));

        return factory;
    }

    /**
     * will retry 3 times if it is failed then send to t-invoice-dead topic
     * @param configurer
     * @param kafkaTemplate
     * @return
     */

    @Bean(name = "invoiceDltContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> invoiceDltContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer, KafkaTemplate<String, String> kafkaTemplate) {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory());

        var recover = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new TopicPartition("t-invoice-dead",record.partition()));

        factory.setCommonErrorHandler(new DefaultErrorHandler(recover,
                new FixedBackOff(1000, 3)));

        return factory;
    }
}
