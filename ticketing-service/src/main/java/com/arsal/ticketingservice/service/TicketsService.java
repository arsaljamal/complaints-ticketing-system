package com.arsal.ticketingservice.service;

import com.arsal.ticketingservice.domain.Tickets;
import com.arsal.ticketingservice.kafkaconsumer.DeliveryDto;
import com.arsal.ticketingservice.repo.TicketsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketsService.class);

    private final TicketsRepository ticketsRepository;

    @Autowired
    public TicketsService(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    public Tickets save(Tickets ticket) {
        return ticketsRepository.save(ticket);
    }

    public Tickets populateTickets(DeliveryDto deliveryDto) {
        LOGGER.debug("Populating Tickets from DeliveryDto");
        Tickets ticket = new Tickets();
        ticket.setDeliveryId(deliveryDto.getId());
        ticket.setCustomerType(deliveryDto.getCustomerType());
        ticket.setDeliveryStatus(deliveryDto.getDeliveryStatus());
        ticket.setRiderRating(deliveryDto.getRiderRating());
        ticket.setTimeToReachDestination(deliveryDto.getTimeToReachDestination());
        ticket.setEstimatedTimeOfDelivery(deliveryDto.getEstimatedTimeOfDelivery());
        ticket.setExpectedDeliveryTime(deliveryDto.getExpectedDeliveryTime());
        return save(ticket);
    }

    public List<Tickets> findAllWithOrder() {
        return ticketsRepository.findAllWithOrder().get();
    }
}
