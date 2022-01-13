package com.arsal.deliveryservice.domain;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

public class DeliveryDetailsTest {

    private final Date nowTimeStamp = new Date();

    public DeliveryDetails setUp() {
        DeliveryDetails deliveryDetails = new DeliveryDetails();
        deliveryDetails.setProcessed(Boolean.TRUE);
        deliveryDetails.setDeliveryStatus(DeliveryStatus.Order_Delivered);
        deliveryDetails.setCustomerType(CustomerType.VIP);
        deliveryDetails.setRiderRating(RiderRating.FOUR);
        deliveryDetails.setCurrentDistanceFromDestinationInMeters(10);
        deliveryDetails.setRestaurantMeanTimeToPrepareFoodInMinutes(10);
        deliveryDetails.setTimeToReachDestination(nowTimeStamp);
        deliveryDetails.setExpectedDeliveryTime(nowTimeStamp);
        deliveryDetails.setEstimatedTimeOfDelivery(nowTimeStamp);
        return deliveryDetails;
    }


    @Test
    public void testDeliveryDetailsGetters() {
        DeliveryDetails deliveryDetailsTest = setUp();
        assertEquals(deliveryDetailsTest.getProcessed(), Boolean.TRUE);
        assertEquals(deliveryDetailsTest.getDeliveryStatus(), DeliveryStatus.Order_Delivered);
        assertEquals(deliveryDetailsTest.getCustomerType(), CustomerType.VIP);
        assertEquals(deliveryDetailsTest.getRiderRating(), RiderRating.FOUR);
        assertEquals(10, (int) deliveryDetailsTest.getCurrentDistanceFromDestinationInMeters());
        assertEquals(10, (int) deliveryDetailsTest.getRestaurantMeanTimeToPrepareFoodInMinutes());
        assertEquals(deliveryDetailsTest.getTimeToReachDestination(), nowTimeStamp);
        assertEquals(deliveryDetailsTest.getExpectedDeliveryTime(), nowTimeStamp);
        assertEquals(deliveryDetailsTest.getEstimatedTimeOfDelivery(), nowTimeStamp);
    }


}
