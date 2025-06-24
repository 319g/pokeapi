package com.alea.pokeapi.config;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {

  private StackTraceElement[] stackTrace;
  private LocalDateTime dateTime;

}
