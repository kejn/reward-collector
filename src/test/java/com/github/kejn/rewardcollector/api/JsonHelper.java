package com.github.kejn.rewardcollector.api;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface JsonHelper {

	default String asJsonString(Object object) {
		try {
			return getObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	ObjectMapper getObjectMapper();
}
