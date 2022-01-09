package com.arsal.ticketingservice.web;

import com.arsal.ticketingservice.domain.Users;
import com.arsal.ticketingservice.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping(path = "/tickets")
public class TicketingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketingController.class);

    private final UsersService usersService;

    public TicketingController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String getTickets() {
        return "Ticket!";
    }

    @PostMapping("/signin")
    public String login(@RequestBody LoginDto loginDto) {
        return usersService.signin(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(()->
                new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Users signup(@RequestBody LoginDto loginDto){
        return usersService.signup(loginDto.getUsername(), loginDto.getPassword())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"User already exists"));
    }
}
