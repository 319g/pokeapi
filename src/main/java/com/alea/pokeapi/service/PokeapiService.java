package com.alea.pokeapi.service;

import java.util.List;

import com.alea.pokeapi.model.Pokemon;

public interface PokeapiService {

  public List<Pokemon> getByWeight(Integer limit);

  public List<Pokemon> getByHeigth(Integer limit);

  public List<Pokemon> getByBaseExperience(Integer limit);
}
