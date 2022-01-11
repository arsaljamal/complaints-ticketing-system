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


@Component
public class DeliveryScheduler {

    private static final Logger log = LoggerFactory.getLogger(DeliveryScheduler.class);

    @Autowired
    private DeliveryDetailsService deliveryDetailsService;

    @Autowired
    private KafkaTemplate<String, DeliveryDto> deliveryDtoKafkaTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Value(value = "${kafka.topic}")
    private String kafkaTopic;

    private DeliveryDto convertToDto(DeliveryDetails deliveryDetails) {
        DeliveryDto deliveryDto = modelMapper.map(deliveryDetails, DeliveryDto.class);
        deliveryDto.setCustomerType(deliveryDetails.getCustomerType().toString());
        deliveryDto.setDeliveryStatus(deliveryDetails.getDeliveryStatus().toString());
        return deliveryDto;
    }

    public void sendMessageToTicketTopic(String topic, DeliveryDto deliveryDto) {
        ListenableFuture<SendResult<String, DeliveryDto>> future = deliveryDtoKafkaTemplate.send(topic, deliveryDto);

        future.addCallback(new ListenableFutureCallback<SendResult<String, DeliveryDto>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("Failure with following error :" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, DeliveryDto> result) {
                log.info("Message : " + deliveryDto + ", Offset : " + result.getRecordMetadata().offset());
            }
        });
    }

    @Scheduled(fixedRate = 5000)
    public void populateDeliveries() {
        log.info("Saving Delivery");
        deliveryDetailsService.populateDeliveryDetailsTable();
    }

    @Scheduled(fixedRate = 5000)
    public void getHighPriorityDeliveries() {
        log.info("Sending Delivery to topic");
        deliveryDetailsService.getHighPriorityDeliveries().
                forEach(deliveryDetails -> {
                            sendMessageToTicketTopic(kafkaTopic, convertToDto(deliveryDetails));
                            deliveryDetailsService.updateDeliverDetailsToProcessed(deliveryDetails);
                        });
    }
}
