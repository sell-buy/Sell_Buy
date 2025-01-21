package com.sell_buy.sell_buy.common.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception e, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("exception");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        log.error("Exception occurred: ", e);
        modelAndView.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        modelAndView.addObject("errorMessage", "Internal server error occurred. Please try again later.");
        return modelAndView;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(AuthenticationException e, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("exception");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        log.error("AuthenticationException occurred: ", e);
        modelAndView.addObject("errorCode", HttpStatus.UNAUTHORIZED.value());
        modelAndView.addObject("errorMessage", "User not authenticated.");
        return modelAndView;
    }


}
