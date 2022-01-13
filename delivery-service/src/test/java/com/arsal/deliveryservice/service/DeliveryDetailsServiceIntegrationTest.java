package com.arsal.deliveryservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DeliveryDetailsServiceIntegrationTest {

    @Autowired
    private DeliveryDetailsService deliveryDetailsService;

    @Test
    public void testPopulateDeliveryDetails() {
        assertEquals(deliveryDetailsService.populateDeliveryDetails().getProcessed(), Boolean.FALSE);
    }
}
