package com.arsal.ticketingservice.service;

import com.arsal.ticketingservice.domain.Tickets;
import com.arsal.ticketingservice.kafkaconsumer.DeliveryDto;
import com.arsal.ticketingservice.repo.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsService {

    private final TicketsRepository ticketsRepository;

    @Autowired
    public TicketsService(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    public void saveTicket(DeliveryDto deliveryDto) {
        Tickets ticket = new Tickets();
        ticket.setDeliveryId(deliveryDto.getId());
        ticket.setCustomerType(deliveryDto.getCustomerType());
        ticket.setDeliveryStatus(deliveryDto.getDeliveryStatus());
        ticket.setRiderRating(deliveryDto.getRiderRating());
        ticket.setEstimatedTimeOfDelivery(deliveryDto.getEstimatedTimeOfDelivery());
        ticket.setExpectedDeliveryTime(deliveryDto.getExpectedDeliveryTime());
        ticketsRepository.save(ticket);
    }

    public List<Tickets> findAllWithOrder() {
        return ticketsRepository.findAllWithOrder().get();
    }
}
