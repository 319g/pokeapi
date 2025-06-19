package com.alea.pokeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alea.pokeapi.config.Response;
import com.alea.pokeapi.constants.ResponseConstants;
import com.alea.pokeapi.model.Pokemon;
import com.alea.pokeapi.service.PokeapiService;

@RestController
@RequestMapping(path = "/pokemon")
public class PokeapiController {

  @Autowired
  private PokeapiService service;

  @GetMapping(value = "/weight")
  public Response<List<Pokemon>> getByWeight() {
    return new Response<>(service.getByWeight(5), ResponseConstants.SUCCESS);
  }
  

}
