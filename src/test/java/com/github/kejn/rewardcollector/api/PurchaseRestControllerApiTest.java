package com.github.kejn.rewardcollector.api;

import static com.github.kejn.rewardcollector.api.PurchaseRestController.ROOT_PATH;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kejn.rewardcollector.data.PurchaseDto;
import com.github.kejn.rewardcollector.service.PurchaseService;
import com.github.kejn.rewardcollector.tag.TestTag;

@Tag(TestTag.API)
@WebMvcTest(PurchaseRestController.class)
class PurchaseRestControllerApiTest implements JsonHelper {

	private static final String SOME_USER_ID = "someUserId";

	@MockBean
	private PurchaseService service;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	@Test
	void shouldRegisterPurchase() throws Exception {
		// given
		PurchaseDto requestBody = PurchaseDto.builder().userIdentifier(SOME_USER_ID)
				.transactionValue(new BigDecimal(55)).placedAt(LocalDateTime.now()).build();
		String path = ROOT_PATH;
		String url = "/" + path;

		// when
		ResultActions result = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestBody)).accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isCreated()).andExpect(content().string(EMPTY));
	}

	@Test
	void shouldFindAllRegisteredTransactionsByUserIdentifier() throws Exception {
		// given
		String path = ROOT_PATH + "/" + SOME_USER_ID;
		String url = "/" + path;

		// when
		ResultActions result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk()).andExpect(content().json(asJsonString(Collections.emptyList())));
	}

	@Test
	void shouldDeleteByUserIdentifier() throws Exception {
		// given
		String path = ROOT_PATH + "/" + SOME_USER_ID;
		String url = "/" + path;

		// when
		ResultActions result = mvc.perform(MockMvcRequestBuilders.delete(url).accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isNoContent());
	}

	@Test
	void shouldPurge() throws Exception {
		// given
		String path = ROOT_PATH;
		String url = "/" + path;

		// when
		ResultActions result = mvc.perform(MockMvcRequestBuilders.delete(url).accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isInternalServerError());
	}

}
