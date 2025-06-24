package com.alea.pokeapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alea.pokeapi.integration.PokeapiFeignClient;
import com.alea.pokeapi.model.Pokemon;
import com.alea.pokeapi.model.PokemonSearchResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PokeapiApplicationTests {

	@MockitoBean
	private PokeapiFeignClient feignClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Value("${pokemonapi.client.limit}")
  private int apiSearchLimit;

	@Test
	public void test10_getByWeight() throws Exception {
		Mockito.when(feignClient.getPokemonList(2000, 1)).thenReturn(getFileAsJson("mockFiles/mockGetPokemonList.json", PokemonSearchResult.class));
		Mockito.when(feignClient.getPokemon("bulbasaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail1.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("ivysaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail2.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("venusaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail3.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charmander")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail4.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charmeleon")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail5.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charizard")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail6.json", Pokemon.class));

		MvcResult response = sendRequest(MockMvcRequestBuilders.get("/pokemon/weight"), "");
		assertSuccess(response, getFileAsString("responseFiles/getByWeigthResponse.json"));
	}

	@Test
	public void test11_getByHeight() throws Exception {
		Mockito.when(feignClient.getPokemonList(2000, 1)).thenReturn(getFileAsJson("mockFiles/mockGetPokemonList.json", PokemonSearchResult.class));
		Mockito.when(feignClient.getPokemon("bulbasaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail1.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("ivysaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail2.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("venusaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail3.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charmander")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail4.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charmeleon")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail5.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charizard")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail6.json", Pokemon.class));

		MvcResult response = sendRequest(MockMvcRequestBuilders.get("/pokemon/height"), "");
		assertSuccess(response, getFileAsString("responseFiles/getByHeightResponse.json"));
	}

	@Test
	public void test12_getByBaseExperience() throws Exception {
		Mockito.when(feignClient.getPokemonList(2000, 1)).thenReturn(getFileAsJson("mockFiles/mockGetPokemonList.json", PokemonSearchResult.class));
		Mockito.when(feignClient.getPokemon("bulbasaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail1.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("ivysaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail2.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("venusaur")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail3.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charmander")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail4.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charmeleon")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail5.json", Pokemon.class));
		Mockito.when(feignClient.getPokemon("charizard")).thenReturn(getFileAsJson("mockFiles/mockGetPokemonDetail6.json", Pokemon.class));

		MvcResult response = sendRequest(MockMvcRequestBuilders.get("/pokemon/base-experience"), "");
		assertSuccess(response, getFileAsString("responseFiles/getByBaseExperienceResponse.json"));
	}

	private String getFileAsString(String filename) throws Exception {
		return objectMapper.writeValueAsString(
			objectMapper.readValue(new ClassPathResource(filename).getFile(), Object.class)
		);
	}

	private MvcResult sendRequest (MockHttpServletRequestBuilder request, String json) throws Exception {
		return mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
	}

	private void assertSuccess(MvcResult result, String expected) throws Exception {
		assertEquals(mvcRequestToString(result), expected);
	}

	private String mvcRequestToString(MvcResult result) throws Exception {
		String content = result.getResponse().getContentAsString();
		JsonNode jsonResponse = objectMapper.readTree(content);
		return objectMapper.writeValueAsString(jsonResponse);
	}

	private <T> T getFileAsJson(String filename, Class<T> clazz) throws Exception {
		ClassPathResource resource = new ClassPathResource(filename);
		return objectMapper.readValue(resource.getFile(), clazz);
	}
}
