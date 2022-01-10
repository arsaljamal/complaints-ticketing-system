package com.arsal.deliveryservice.service;

import com.arsal.deliveryservice.domain.CustomerType;
import com.arsal.deliveryservice.domain.DeliveryDetails;
import com.arsal.deliveryservice.domain.DeliveryStatus;
import com.arsal.deliveryservice.domain.RiderRating;
import com.arsal.deliveryservice.repo.DeliveryDetailsRepository;
import com.arsal.deliveryservice.utility.RandomUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class DeliveryDetailsService {

    private final DeliveryDetailsRepository deliveryDetailsRepository;

    @Autowired
    public DeliveryDetailsService(DeliveryDetailsRepository deliveryDetailsRepository) {
        this.deliveryDetailsRepository = deliveryDetailsRepository;
    }

    public void populateDeliveryDetailsTable() {
        DeliveryDetails deliveryDetails = new DeliveryDetails();

        deliveryDetails.setDeliveryStatus(RandomUtility.randomEnum(DeliveryStatus.class));
        deliveryDetails.setCustomerType(RandomUtility.randomEnum(CustomerType.class));
        deliveryDetails.setRiderRating(RandomUtility.randomEnum(RiderRating.class));

        deliveryDetails.setCurrentDistanceFromDestinationInMeters(RandomUtility.getRandomNumberUsingNextInt(10,50));
        deliveryDetails.setRestaurantMeanTimeToPrepareFoodInMinutes(RandomUtility.getRandomNumberUsingNextInt(15,50));

        LocalDateTime now = LocalDateTime.now();
        now.with(LocalTime.MIN);
        Date startOfDay = Date.from(now.with(LocalTime.MIN).toInstant(ZoneOffset.UTC));
        Date nowTimeStamp = new Date();
        deliveryDetails.setTimeToReachDestination(RandomUtility.between(startOfDay, nowTimeStamp));

        //ExpectedDeliveryTime = RestaurantMeanTimeToPrepareFoodInMinutes + TimeToReachDestination
        Date expectedDeliveryTime = new Date(deliveryDetails.getRestaurantMeanTimeToPrepareFoodInMinutes().longValue()
                + deliveryDetails.getTimeToReachDestination().getTime());
        deliveryDetails.setExpectedDeliveryTime(expectedDeliveryTime);

        deliveryDetailsRepository.save(deliveryDetails);
    }

    public void getHighPriorityDeliveries() {

    }
}
