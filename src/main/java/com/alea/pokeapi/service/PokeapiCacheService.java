package com.alea.pokeapi.service;

import com.alea.pokeapi.model.Pokemon;

public interface PokeapiCacheService {

  public Pokemon getPokemonCache(String name);
}
