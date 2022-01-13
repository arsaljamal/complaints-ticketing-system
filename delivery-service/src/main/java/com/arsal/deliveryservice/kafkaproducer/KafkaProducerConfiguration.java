package com.arsal.deliveryservice.kafkaproducer;

import com.arsal.deliveryservice.scheduler.DeliveryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerConfiguration.class);

    @Value(value = "${kafka.broker.url}")
    private String kafkaAddress;

    @Bean
    public ProducerFactory<String, DeliveryDto> deliveryDtoProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, DeliveryDto> deliveryDtoKafkaTemplate() {
        LOGGER.info("Creating kafka template to send Delivery to topic.");
        return new KafkaTemplate<>(deliveryDtoProducerFactory());
    }
}
