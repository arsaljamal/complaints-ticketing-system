package com.arsal.ticketingservice.security;

import com.arsal.ticketingservice.domain.Users;
import com.arsal.ticketingservice.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class TicketUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public TicketUserDetailsService(UsersRepository usersRepository, JwtConfiguration jwtConfiguration) {
        this.usersRepository = usersRepository;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with name %s does not exist", username)));

        return withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public Optional<UserDetails> loadUserByJwtTokenAndDatabase(String jwtToken) {
        if (jwtConfiguration.isValidToken(jwtToken)) {
            return Optional.of(loadUserByUsername(jwtConfiguration.getUsername(jwtToken)));
        } else {
            return Optional.empty();
        }
    }

}
