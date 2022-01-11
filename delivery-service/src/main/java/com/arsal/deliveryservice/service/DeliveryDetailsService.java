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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeliveryDetailsService {

    private final DeliveryDetailsRepository deliveryDetailsRepository;

    @Autowired
    public DeliveryDetailsService(DeliveryDetailsRepository deliveryDetailsRepository) {
        this.deliveryDetailsRepository = deliveryDetailsRepository;
    }

    public void updateDeliverDetailsToProcessed(DeliveryDetails deliveryDetails) {
        deliveryDetails.setProcessed(Boolean.TRUE);
        deliveryDetailsRepository.save(deliveryDetails);
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

        long bufferTime = (long) RandomUtility.getRandomNumberUsingNextInt(15,50) * 60 * 60;
        Date expectedDeliveryTime = new Date(deliveryDetails.getTimeToReachDestination().getTime()
                                                + bufferTime);
        deliveryDetails.setExpectedDeliveryTime(expectedDeliveryTime);

        //EstimatedTimeOfDeliveryTime = RestaurantMeanTimeToPrepareFoodInMinutes + TimeToReachDestination
        long  restaurantMeanTimeToPrepareFood = (long) deliveryDetails.
                                                            getRestaurantMeanTimeToPrepareFoodInMinutes() * 60 * 60;
        Date estimatedTimeOfDelivery = new Date( restaurantMeanTimeToPrepareFood
                + deliveryDetails.getTimeToReachDestination().getTime());

        deliveryDetails.setEstimatedTimeOfDelivery(estimatedTimeOfDelivery);

        deliveryDetailsRepository.save(deliveryDetails);
    }

    public List<DeliveryDetails> getHighPriorityDeliveries() {

        List<DeliveryDetails> deliveryDetailsListByCustomerType = deliveryDetailsRepository.
                findAllByCustomerTypeAndDeliveryStatusNotAndIsProcessed(CustomerType.VIP,
                        DeliveryStatus.Order_Delivered, Boolean.FALSE).get();

        List<DeliveryDetails> deliveryDetailsListByExpectedDeliveryTimeHasPassed = deliveryDetailsRepository.
            findAllByExpectedDeliveryTimeIsLessThanAndDeliveryStatusIsNotAndIsProcessed(new Date(),
                        DeliveryStatus.Order_Delivered, Boolean.FALSE).get();

        List<DeliveryDetails> deliveryDetailsListByEstimatedTimeOfDeliveryGreaterThanExpectedDeliveryTime =
                deliveryDetailsRepository.
                findAllDeliveriesWhenEstimatedTimeOfDeliveryIsGreaterThanExpectedDeliveryTimeAndIsProcessed(
                        DeliveryStatus.Order_Delivered, Boolean.FALSE).get();

        return Stream.of(deliveryDetailsListByCustomerType,
                deliveryDetailsListByExpectedDeliveryTimeHasPassed,
                deliveryDetailsListByEstimatedTimeOfDeliveryGreaterThanExpectedDeliveryTime).
                flatMap(Collection::stream).collect(Collectors.toList());
    }
}
