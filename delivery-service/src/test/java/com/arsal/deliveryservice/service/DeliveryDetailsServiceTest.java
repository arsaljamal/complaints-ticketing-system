package com.arsal.deliveryservice.service;

import com.arsal.deliveryservice.domain.CustomerType;
import com.arsal.deliveryservice.domain.DeliveryDetails;
import com.arsal.deliveryservice.domain.DeliveryDetailsTest;
import com.arsal.deliveryservice.domain.DeliveryStatus;
import com.arsal.deliveryservice.repo.DeliveryDetailsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryDetailsServiceTest {

    @Mock
    private DeliveryDetailsRepository deliveryDetailsRepository;

    @InjectMocks
    private DeliveryDetailsService deliveryDetailsService;

    private final DeliveryDetailsTest deliveryDetailsTest = new DeliveryDetailsTest();

    private final DeliveryDetails deliveryDetails = deliveryDetailsTest.setUp();

    private final Date nowTimeStamp = new Date();

    @Before
    public void setUpReturnValuesOfMockMethods() {
        when(deliveryDetailsRepository.save(deliveryDetails)).thenReturn(deliveryDetails);
    }

    @Test
    public void testPopulateDeliveryDetails() {
        assertEquals(deliveryDetailsService.save(deliveryDetails), deliveryDetails);
    }

    @Test
    public void testGetHighPriorityDeliveries() {
        when(deliveryDetailsRepository.
                findAllByCustomerTypeAndDeliveryStatusNotAndIsProcessed(CustomerType.VIP,
                DeliveryStatus.Order_Delivered, Boolean.FALSE)).
                thenReturn(Optional.of(Collections.singletonList(deliveryDetails)));

        when(deliveryDetailsRepository.
                findAllByExpectedDeliveryTimeIsLessThanAndDeliveryStatusIsNotAndIsProcessed(nowTimeStamp,
                        DeliveryStatus.Order_Delivered, Boolean.FALSE)).
                thenReturn(Optional.of(Collections.singletonList(deliveryDetails)));

        when(deliveryDetailsRepository.
                findAllDeliveriesWhenEstimatedTimeOfDeliveryIsGreaterThanExpectedDeliveryTimeAndIsProcessed(
                DeliveryStatus.Order_Delivered, Boolean.FALSE)).
                thenReturn(Optional.of(Collections.singletonList(deliveryDetails)));

        assertEquals(deliveryDetailsService.getHighPriorityDeliveries(nowTimeStamp),
                Arrays.asList(deliveryDetails,deliveryDetails,deliveryDetails));
    }
}
