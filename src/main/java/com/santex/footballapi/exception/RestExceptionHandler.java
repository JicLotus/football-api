package com.santex.footballapi.exception;

import javax.persistence.EntityNotFoundException;

import com.santex.footballapi.dto.response.Response;
import com.santex.footballapi.exception.FootballApiExceptions.LeagueCodeAlreadyImportedException;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response<Object> response = Response.badRequest();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleHttpClientError(
            HttpClientErrorException ex) {
        Response<Object> response = Response.serverError();
        return new ResponseEntity<>(response, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(JDBCConnectionException.class)
    protected ResponseEntity<Object> handleJDBCConnection(
            JDBCConnectionException ex) {
        Response<Object> response = Response.serverError();
        return new ResponseEntity<>(response, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(LeagueCodeAlreadyImportedException.class)
    protected ResponseEntity<Object> handleLeagueCodeAlreadyImported(
            LeagueCodeAlreadyImportedException ex) {
        Response<Object> response = Response.leagueCodeAlreadyImportedException();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        Response<Object> response = Response.notFound();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response<Object> response = Response.badRequest();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}