package com.alea.pokeapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PokeapiApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Value("${pokemonapi.client.limit}")
  private int apiSearchLimit;

	@Test
	public void test10_getByWeight() throws Exception {
		MvcResult response = sendRequest(MockMvcRequestBuilders.get("/pokemon/weight"), "");
		assertSuccess(response, getFileAsString("responseFiles/getByWeigthResponse.json"));
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
}
