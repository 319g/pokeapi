package com.alea.pokeapi.config;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomErrorException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final boolean success;

  public CustomErrorException(HttpStatus httpStatus, String errorMessage) {
    super(errorMessage);
    this.httpStatus = httpStatus;
    this.success = false;
  }

}
