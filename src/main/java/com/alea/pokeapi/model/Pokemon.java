package com.alea.pokeapi.model;

import lombok.Data;

@Data
public class Pokemon {

  private Long id;
  private String name;
  private Integer baseExperience;
  private Integer height;
  private Integer weight;
}
