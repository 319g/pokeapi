package com.alea.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Pokemon {

  private Long id;
  private String name;
  @JsonAlias("base_experience")
  private Integer baseExperience;
  private Integer height;
  private Integer weight;
}
