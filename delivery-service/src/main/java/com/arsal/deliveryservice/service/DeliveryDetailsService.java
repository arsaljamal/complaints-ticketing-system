package com.arsal.deliveryservice.service;

import com.arsal.deliveryservice.repo.DeliveryDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryDetailsService {

    private final DeliveryDetailsRepository deliveryDetailsRepository;

    @Autowired
    public DeliveryDetailsService(DeliveryDetailsRepository deliveryDetailsRepository) {
        this.deliveryDetailsRepository = deliveryDetailsRepository;
    }

    public void populateDeliveryDetailsTable() {

    }

    public void getHighPriorityDeliveries() {

    }
}
