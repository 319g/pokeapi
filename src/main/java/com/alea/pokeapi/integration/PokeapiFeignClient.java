package com.alea.pokeapi.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.alea.pokeapi.model.Pokemon;
import com.alea.pokeapi.model.PokemonSearchResult;

@FeignClient(name = "POKEAPI", url = "${pokemonapi.client.url}")
public interface PokeapiFeignClient {

  @GetMapping()
  PokemonSearchResult getPokemonList(@RequestParam("limit") Integer limit);

  @GetMapping(value = "{name}")
  Pokemon getPokemon(@PathVariable("name") String name);

}
