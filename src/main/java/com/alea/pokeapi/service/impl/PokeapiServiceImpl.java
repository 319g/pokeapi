package com.alea.pokeapi.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alea.pokeapi.integration.PokeapiFeignClient;
import com.alea.pokeapi.model.Pokemon;
import com.alea.pokeapi.model.PokemonSearch;
import com.alea.pokeapi.model.PokemonSearchResult;
import com.alea.pokeapi.service.PokeapiCacheService;
import com.alea.pokeapi.service.PokeapiService;

@Service
public class PokeapiServiceImpl implements PokeapiService {

  @Autowired
  PokeapiCacheService cacheService;

  private PokeapiFeignClient feignClient;

  @Value("${pokemonapi.client.limit}")
  private int apiSearchLimit;

  public PokeapiServiceImpl(PokeapiFeignClient feignClient) {
    this.feignClient = feignClient;
  }

  @Override
  public List<Pokemon> getByWeight(Integer limit) {
    return getPokemonList(limit, Pokemon::getWeight);
  }

  @Override
  public List<Pokemon> getByHeigth(Integer limit) {
    return getPokemonList(limit, Pokemon::getHeight);
  }

  @Override
  public List<Pokemon> getByBaseExperience(Integer limit) {
    return getPokemonList(limit, Pokemon::getBaseExperience);
  }

  private <T extends Comparable<? super T>> List<Pokemon> getPokemonList(Integer limit, Function<Pokemon, T> orderParam) {
    PokemonSearchResult searchResult = feignClient.getPokemonList(apiSearchLimit);
    List<PokemonSearch> pokemonResults = searchResult.getResults();

    List<Pokemon> pokemonsDetails = pokemonResults.parallelStream()
      .map(result -> cacheService.getPokemonCache(result.getName()))
      .filter(Objects::nonNull)
      .sorted(Comparator.comparing(orderParam).reversed())
      .limit(limit)
      .toList();

    return pokemonsDetails;
  }

  
}
