package com.arsal.ticketingservice.kafkaconsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerListener {

    @Value(value = "${kafka.topic}")
    private String kafkaTopic;

    @KafkaListener(
            topics = "tickets",
            containerFactory = "deliveryDtoConcurrentKafkaListenerContainerFactory")
    public void deliveryDtoListener(DeliveryDto deliveryDto) {
        System.out.println("DeliveryDto :" + deliveryDto.toString() + " Received by Consumer.");
    }
}
