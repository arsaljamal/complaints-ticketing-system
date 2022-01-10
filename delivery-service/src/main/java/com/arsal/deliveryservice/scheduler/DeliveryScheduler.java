package com.arsal.deliveryservice.scheduler;

import com.arsal.deliveryservice.service.DeliveryDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Component
public class DeliveryScheduler {

    private static final Logger log = LoggerFactory.getLogger(DeliveryScheduler.class);

    @Autowired
    private DeliveryDetailsService deliveryDetailsService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("Saving Delivery");
        deliveryDetailsService.populateDeliveryDetailsTable();
    }
}
