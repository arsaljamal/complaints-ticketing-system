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

    Optional<List<DeliveryDetails>> findAllByExpectedDeliveryTimeIsLessThanAndDeliveryStatusIsNot(Date nowTimestamp,
                                                                                               DeliveryStatus deliveryStatus);

    Optional<List<DeliveryDetails>> findAllByCustomerTypeAndDeliveryStatusNot(CustomerType customerType, DeliveryStatus deliveryStatus);

    @Query("SELECT delivery FROM DeliveryDetails delivery where delivery.expectedDeliveryTime < delivery.estimatedTimeOfDelivery and not delivery.deliveryStatus = ?1")
    Optional<List<DeliveryDetails>> findAllDeliveriesWhenEstimatedTimeOfDeliveryIsGreaterThanExpectedDeliveryTime(DeliveryStatus deliveryStatus);
}
