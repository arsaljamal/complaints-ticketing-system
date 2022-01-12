package com.arsal.ticketingservice.repo;

import com.arsal.ticketingservice.domain.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {

    @Query(value = "SELECT t from Tickets t order by t.customerType desc , t.expectedDeliveryTime, t.estimatedTimeOfDelivery ")
    Optional<List<Tickets>> findAllWithOrder();
}
