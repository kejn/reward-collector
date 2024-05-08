package com.github.kejn.rewardcollector.api;

import static com.github.kejn.rewardcollector.api.RewardPointsRestController.ROOT_PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.YearMonth;

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
import com.github.kejn.rewardcollector.data.PointsSummary;
import com.github.kejn.rewardcollector.service.PointsSummaryService;
import com.github.kejn.rewardcollector.service.PurchaseService;
import com.github.kejn.rewardcollector.tag.TestTag;

@Tag(TestTag.API)
@WebMvcTest(RewardPointsRestController.class)
class RewardPointsRestControllerApiTest implements JsonHelper {

	private static final String SOME_USER_ID = "someUserId";

	@MockBean
	private PointsSummaryService pointsSummaryService;

	@MockBean
	private PurchaseService purchaseService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	@Test
	void shouldReturnTotalPoints() throws Exception {
		String path = ROOT_PATH;
		String url = "/" + path + "/" + SOME_USER_ID + "/total";
		String expectedResponse = asJsonString(new PointsSummary(SOME_USER_ID, 0L, null));

		// when
		ResultActions result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk()).andExpect(content().json(expectedResponse));
	}

	@Test
	void shouldReturnPointsFromLastMonth() throws Exception {
		String path = ROOT_PATH;
		String url = "/" + path + "/" + SOME_USER_ID + "/monthly?monthsBack=1";
		String expectedResponse = asJsonString(new PointsSummary(SOME_USER_ID, 0L, YearMonth.now().minusMonths(1L)));

		// when
		ResultActions result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk()).andExpect(content().json(expectedResponse));
	}

}
