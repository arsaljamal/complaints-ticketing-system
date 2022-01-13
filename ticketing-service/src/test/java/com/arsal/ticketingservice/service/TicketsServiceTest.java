package com.arsal.ticketingservice.service;

import com.arsal.ticketingservice.domain.Tickets;
import com.arsal.ticketingservice.domain.TicketsTest;
import com.arsal.ticketingservice.repo.TicketsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketsServiceTest {

    @Mock
    private TicketsRepository ticketsRepository;

    @InjectMocks
    private TicketsService ticketsService;

    private final TicketsTest ticketsTest = new TicketsTest();

    private final Tickets tickets = ticketsTest.setUp();

    @Before
    public void setUpReturnValuesOfMockMethods() {
        when(ticketsRepository.save(tickets)).thenReturn(tickets);
    }

    @Test
    public void testSaveTickets() {
        assertEquals(ticketsService.save(tickets), tickets);
    }
}
