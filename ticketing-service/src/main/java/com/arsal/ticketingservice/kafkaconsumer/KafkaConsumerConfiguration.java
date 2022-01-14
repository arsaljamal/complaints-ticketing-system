package com.arsal.ticketingservice.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfiguration.class);

    @Value(value = "${kafka.broker.url}")
    private String kafkaAddress;

    @Bean
    public ConsumerFactory<String, DeliveryDto> deliveryDtoConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<DeliveryDto> deliveryDtoJsonDeserializer = new JsonDeserializer<>(DeliveryDto.class);
        deliveryDtoJsonDeserializer.addTrustedPackages("com.arsal.deliveryservice.scheduler.DeliveryDto");
        deliveryDtoJsonDeserializer.setRemoveTypeHeaders(false);
        deliveryDtoJsonDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deliveryDtoJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeliveryDto> deliveryDtoConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeliveryDto>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deliveryDtoConsumerFactory());
        return factory;
    }
}
