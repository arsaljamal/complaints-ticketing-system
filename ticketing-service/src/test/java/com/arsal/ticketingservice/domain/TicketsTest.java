package com.arsal.ticketingservice.domain;


import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TicketsTest {

    private final Date nowTimeStamp = new Date();
    private static final String Order_Delivered = "Order_Delivered";
    private static final String VIP = "VIP";
    private static final String FOUR = "FOUR";

    public Tickets setUp() {
        Tickets tickets = new Tickets();
        tickets.setDeliveryId(1L);
        tickets.setDeliveryStatus(Order_Delivered);
        tickets.setCustomerType(VIP);
        tickets.setRiderRating(FOUR);
        tickets.setTimeToReachDestination(nowTimeStamp);
        tickets.setExpectedDeliveryTime(nowTimeStamp);
        tickets.setEstimatedTimeOfDelivery(nowTimeStamp);

        return tickets;
    }

    @Test
    public void testTicketsGetters() {
        Tickets ticketsTest = setUp();
        assertEquals(ticketsTest.getDeliveryId(), Long.valueOf(1L));
        assertEquals(ticketsTest.getDeliveryStatus(), Order_Delivered);
        assertEquals(ticketsTest.getCustomerType(), VIP);
        assertEquals(ticketsTest.getRiderRating(), FOUR);
        assertEquals(ticketsTest.getTimeToReachDestination(), nowTimeStamp);
        assertEquals(ticketsTest.getExpectedDeliveryTime(), nowTimeStamp);
        assertEquals(ticketsTest.getEstimatedTimeOfDelivery(), nowTimeStamp);
    }
}
