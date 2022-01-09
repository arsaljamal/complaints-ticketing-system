package com.arsal.ticketingservice.service;

import com.arsal.ticketingservice.domain.Roles;
import com.arsal.ticketingservice.domain.Users;
import com.arsal.ticketingservice.repo.RolesRepository;
import com.arsal.ticketingservice.repo.UsersRepository;
import com.arsal.ticketingservice.security.JwtConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);

    private UsersRepository usersRepository;

    private AuthenticationManager authenticationManager;

    private RolesRepository rolesRepository;

    private PasswordEncoder passwordEncoder;

    private JwtConfiguration jwtConfiguration;

    @Autowired
    public UsersService(UsersRepository usersRepository, AuthenticationManager authenticationManager,
                        RolesRepository rolesRepository, PasswordEncoder passwordEncoder,
                        JwtConfiguration jwtConfiguration) {
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfiguration = jwtConfiguration;
    }

    public Optional<String> signin(String username, String password) {
        LOGGER.info("New user attempting to sign in");
        Optional<String> token = Optional.empty();
        Optional<Users> user = usersRepository.findByUsername(username);
        if (user.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                token = Optional.of(jwtConfiguration.createToken(username, user.get().getRoles()));
            } catch (AuthenticationException e){
                LOGGER.info("Log in failed for user {}", username);
            }
        }
        return token;
    }

    public Optional<Users> signup(String username, String password) {
        LOGGER.info("New user attempting to sign in");
        Optional<Users> user = Optional.empty();
        if (!usersRepository.findByUsername(username).isPresent()) {
            Optional<Roles> role = rolesRepository.findByRoleName("ROLE_CSR");
            user = Optional.of(usersRepository.save(new Users(username,
                    passwordEncoder.encode(password),
                    role.get())));
        }
        return user;
    }
}
