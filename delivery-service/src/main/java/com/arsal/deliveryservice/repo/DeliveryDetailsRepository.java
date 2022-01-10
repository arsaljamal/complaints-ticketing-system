package com.arsal.deliveryservice.repo;

import com.arsal.deliveryservice.domain.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long> {
}
