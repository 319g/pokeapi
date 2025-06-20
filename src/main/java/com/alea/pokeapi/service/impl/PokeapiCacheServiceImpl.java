package com.alea.pokeapi.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.alea.pokeapi.integration.PokeapiFeignClient;
import com.alea.pokeapi.model.Pokemon;
import com.alea.pokeapi.service.PokeapiCacheService;

@Service
public class PokeapiCacheServiceImpl implements PokeapiCacheService {

  private final Map<String, Pokemon> pokemonCache = new ConcurrentHashMap<>();

  private PokeapiFeignClient feignClient;

  public PokeapiCacheServiceImpl(PokeapiFeignClient feignClient) {
    this.feignClient = feignClient;
  }

  public Pokemon getPokemonCache(String name) {
    return pokemonCache.computeIfAbsent(name, key -> {
      try {
        return feignClient.getPokemon(key);
      } catch (Exception e) {
        return null;
      }
    });
  }
}
