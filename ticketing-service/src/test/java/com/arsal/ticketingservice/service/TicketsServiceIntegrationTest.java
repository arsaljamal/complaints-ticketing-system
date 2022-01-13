package com.arsal.ticketingservice.service;

import com.arsal.ticketingservice.kafkaconsumer.DeliveryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TicketsServiceIntegrationTest {

    @Autowired
    private TicketsService ticketsService;

    private final Date nowTimeStamp = new Date();
    private static final String Order_Delivered = "Order_Delivered";
    private static final String VIP = "VIP";
    private static final String FOUR = "FOUR";

    @Test
    public void testPopulateDeliveryDetails() {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(1L);
        deliveryDto.setDeliveryStatus(Order_Delivered);
        deliveryDto.setCustomerType(VIP);
        deliveryDto.setRiderRating(FOUR);
        deliveryDto.setTimeToReachDestination(nowTimeStamp);
        deliveryDto.setExpectedDeliveryTime(nowTimeStamp);
        deliveryDto.setEstimatedTimeOfDelivery(nowTimeStamp);
        assertEquals(ticketsService.populateTickets(deliveryDto).getDeliveryId(), Long.valueOf(1L));
    }
}
