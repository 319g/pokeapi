package com.alea.pokeapi.config;

import lombok.Data;

@Data
public class Response<T> {

  protected Boolean success;
  protected Integer errorCode;
  protected String message;
  protected T response;

  public Response(T response, String message) {
    this(true, null, message, response);
  }

  public Response(Boolean success, Integer errorCode, String message, T response) {
    super();
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
    this.response = response;
  }
}
