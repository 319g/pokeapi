package com.alea.pokeapi.model;

import java.util.List;

import lombok.Data;

@Data
public class PokemonSearchResult {

  private Integer count;
  private String next;
  private String previous;
  private List<PokemonSearch> results;
}
