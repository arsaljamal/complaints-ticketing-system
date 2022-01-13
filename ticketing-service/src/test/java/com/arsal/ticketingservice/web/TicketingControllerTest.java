package com.arsal.ticketingservice.web;

import com.arsal.ticketingservice.domain.Roles;
import com.arsal.ticketingservice.domain.Tickets;
import com.arsal.ticketingservice.domain.TicketsTest;
import com.arsal.ticketingservice.domain.Users;
import com.arsal.ticketingservice.security.JwtConfiguration;
import com.arsal.ticketingservice.service.TicketsService;
import com.arsal.ticketingservice.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TicketingControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @MockBean
    private UsersService usersService;

    @MockBean
    private TicketsService ticketsService;

    private TicketsTest ticketsTest = new TicketsTest();

    private LoginDto loginDto = new LoginDto("arsal", "admin123");

    public HttpHeaders withRole(String userName, String roleName){
        HttpHeaders headers = new HttpHeaders();
        Roles role = new Roles();
        role.setRoleName(roleName);
        String token =  jwtConfiguration.createToken(userName, Collections.singletonList(role));
        headers.setContentType(APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

    @Test
    public void testSignIn() {
        testRestTemplate.postForEntity("/signin",
                loginDto, Void.class);
        verify(usersService).signin("arsal","admin123");
    }

    @Test
    public void testSignUp() {
        LoginDto signup = new LoginDto("james", "pass");
        Optional<Users> user = Optional.of(new Users(signup.getUsername(), signup.getPassword(), new Roles()));
        when(usersService.signup(signup.getUsername(), signup.getPassword())).thenReturn(user);

        ResponseEntity<Users> response = testRestTemplate.exchange("/signup",
                POST,
                new HttpEntity(signup,withRole("admin","ROLE_ADMIN")),
                Users.class);

        assertEquals(response.getStatusCode().value(), 201);
        assertEquals(response.getBody().getUsername(), user.get().getUsername());
        assertEquals(response.getBody().getRoles().size(), user.get().getRoles().size());
    }

    @Test
    public void testGetTickets() {
        Tickets tickets = ticketsTest.setUp();
        List<Tickets> ticketsList = Collections.singletonList(tickets);

        when(ticketsService.findAllWithOrder()).thenReturn(ticketsList);

        ResponseEntity<Tickets []> response = testRestTemplate.exchange("/tickets",
                GET,
                new HttpEntity(withRole("arsal","ROLE_CSR")),
                Tickets[].class);

        Tickets[] ticketsResponse = response.getBody();
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(Arrays.stream(ticketsResponse).findFirst().get(), tickets);
    }
}
