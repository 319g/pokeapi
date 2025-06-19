package com.alea.pokeapi.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.alea.pokeapi.integration.PokeapiFeignClient;
import com.alea.pokeapi.model.Pokemon;
import com.alea.pokeapi.model.PokemonSearch;
import com.alea.pokeapi.model.PokemonSearchResult;
import com.alea.pokeapi.service.PokeapiService;

@Service
public class PokeapiServiceImpl implements PokeapiService {

  private PokeapiFeignClient feignClient;

  public PokeapiServiceImpl(PokeapiFeignClient feignClient) {
    this.feignClient = feignClient;
  }

  @Override
  public List<Pokemon> getByWeight(Integer limit) {
    int apiSearchLimit = 2000; //todo cambiar por get count
    PokemonSearchResult searchResult = feignClient.getPokemonList(apiSearchLimit);
    List<PokemonSearch> pokemonResults = searchResult.getResults();
    List<Pokemon> allPokemon = pokemonResults.parallelStream()
      .map(result -> {
        try {
          return feignClient.getPokemon(result.getName());
        } catch (Exception e) {
          return null;
        }
      })
      .filter(Objects::nonNull)
      .sorted(Comparator.comparing(Pokemon::getWeight).reversed())
      .limit(limit)
      .toList();

    return allPokemon;
  }
}
