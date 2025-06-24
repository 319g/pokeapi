package com.alea.pokeapi.config;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

  protected static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

  @ExceptionHandler(CustomErrorException.class)
  public ResponseEntity<Response<ResponseError>> handleException(CustomErrorException e, ServletRequest request) {
    Response<ResponseError> response = getResultJsonError(e.getStackTrace(), e.getHttpStatus(), e.getMessage(), request);
    return new ResponseEntity<>(response, e.getHttpStatus());
  }

  private Response<ResponseError> getResultJsonError(StackTraceElement[] stackTraceElements, HttpStatus httpStatus,
      String message, ServletRequest request) {

    HttpServletRequest httpRequest = (HttpServletRequest) request;

    ResponseError error = new ResponseError(stackTraceElements, LocalDateTime.now());
    Response<ResponseError> response = new Response<>(false, httpStatus.value(), message, error);
    printLogError(httpRequest, stackTraceElements, response);
    return response;
  }

  private static void printLogError(HttpServletRequest request,
      StackTraceElement[] stackTraceElements, Response<ResponseError> response) {
    LOGGER.error("=========================== ERROR ================================================");
    LOGGER.error("URI         : {}", request.getRequestURI());
    LOGGER.error("Method      : {}", request.getMethod());
    LOGGER.error("Headers     : {} ", request.getHeaderNames());
    request.getHeaderNames().asIterator().forEachRemaining(
        headerName -> LOGGER.error("              {}", headerName + ": " + request.getHeader(headerName)));
    LOGGER.error("Exception ");
    LOGGER.error("{} ", response.getMessage());
    for (StackTraceElement elem : stackTraceElements) {
      LOGGER.error("{}", elem);
    }
    LOGGER.error("========================== ERROR ===================================================");
  }
}
