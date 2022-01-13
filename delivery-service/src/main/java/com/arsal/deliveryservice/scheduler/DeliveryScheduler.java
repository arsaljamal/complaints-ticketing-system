package com.arsal.deliveryservice.scheduler;

import com.arsal.deliveryservice.domain.DeliveryDetails;
import com.arsal.deliveryservice.service.DeliveryDetailsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;


@Component
public class DeliveryScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryScheduler.class);

    @Autowired
    private DeliveryDetailsService deliveryDetailsService;

    @Autowired
    private KafkaTemplate<String, DeliveryDto> deliveryDtoKafkaTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Value(value = "${kafka.topic}")
    private String kafkaTopic;

    private DeliveryDto convertToDto(DeliveryDetails deliveryDetails) {
        return modelMapper.map(deliveryDetails, DeliveryDto.class);
    }

    public void sendMessageToTicketTopic(String topic, DeliveryDto deliveryDto) {
        ListenableFuture<SendResult<String, DeliveryDto>> future = deliveryDtoKafkaTemplate.send(topic, deliveryDto);

        future.addCallback(new ListenableFutureCallback<SendResult<String, DeliveryDto>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("Failure with following error :" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, DeliveryDto> result) {
                LOGGER.debug("Message : " + deliveryDto + ", Offset : " + result.getRecordMetadata().offset());
            }
        });
    }

    @Scheduled(fixedRate = 5000)
    public void populateDeliveries() {
        LOGGER.debug("Saving Delivery");
        deliveryDetailsService.populateDeliveryDetails();
    }

    @Scheduled(fixedRate = 5000)
    public void getHighPriorityDeliveries() {
        LOGGER.debug("Sending Delivery to kafka topic");
        Date nowTimeStamp = new Date();
        deliveryDetailsService.getHighPriorityDeliveries(nowTimeStamp).
                forEach(deliveryDetails -> {
                            sendMessageToTicketTopic(kafkaTopic, convertToDto(deliveryDetails));
                            deliveryDetailsService.updateDeliverDetailsToProcessed(deliveryDetails);
                        });
    }
}
