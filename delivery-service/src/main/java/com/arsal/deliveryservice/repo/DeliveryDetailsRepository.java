package com.arsal.deliveryservice.repo;

import com.arsal.deliveryservice.domain.CustomerType;
import com.arsal.deliveryservice.domain.DeliveryDetails;
import com.arsal.deliveryservice.domain.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long> {

    Optional<List<DeliveryDetails>> findAllByExpectedDeliveryTimeIsLessThanAndDeliveryStatusIsNotAndIsProcessed(Date nowTimestamp,
                                                                                               DeliveryStatus deliveryStatus, Boolean isProcessed);

    Optional<List<DeliveryDetails>> findAllByCustomerTypeAndDeliveryStatusNotAndIsProcessed(CustomerType customerType, DeliveryStatus deliveryStatus, Boolean isProcessed);

    @Query("SELECT delivery FROM DeliveryDetails delivery where delivery.expectedDeliveryTime < delivery.estimatedTimeOfDelivery and not delivery.deliveryStatus = ?1 and delivery.isProcessed = ?2")
    Optional<List<DeliveryDetails>> findAllDeliveriesWhenEstimatedTimeOfDeliveryIsGreaterThanExpectedDeliveryTimeAndIsProcessed(DeliveryStatus deliveryStatus, Boolean isProcessed);
}
