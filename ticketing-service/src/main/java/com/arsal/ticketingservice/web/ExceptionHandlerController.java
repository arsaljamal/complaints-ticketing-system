package com.arsal.ticketingservice.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException ex, HttpServletResponse res) throws IOException {
        LOGGER.debug("Handled Access Denied Exception", ex);
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public void handleHttpServerErrorException(HttpServerErrorException ex, HttpServletResponse res) throws IOException {
        LOGGER.debug("Handled Http Server Error Exception", ex);
        res.sendError(ex.getStatusCode().value(),ex.getMessage());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public void handleInsufficientAuthenticationException(InsufficientAuthenticationException ex, HttpServletResponse res) throws IOException {
        LOGGER.error("Handled Insufficient Authentication Exception", ex);
        res.sendError(HttpStatus.FORBIDDEN.value(), "Insufficient Authentication");
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletResponse res) throws IOException {
        LOGGER.error("Handled Internal Error Exception", ex);
        res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong");
    }
}
