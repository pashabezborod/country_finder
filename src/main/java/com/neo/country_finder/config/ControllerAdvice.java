package com.neo.country_finder.config;

import com.neo.country_finder.exception.CountryNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.io.IOException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public void handleCountryNotFoundException(HandlerMethodValidationException e, HttpServletResponse response) throws IOException {
        processResponse(400, e.getAllValidationResults().getFirst().getResolvableErrors().getFirst().getDefaultMessage(), response);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public void handleCountryNotFoundException(CountryNotFoundException e, HttpServletResponse response) throws IOException {
        processResponse(500, e.getMessage(), response);
    }

    private void processResponse(int code, String message, HttpServletResponse response) throws IOException {
        response.setStatus(code);
        response.getWriter().write(message);
        response.setHeader("Content-Type", "Application/json");
    }
}
