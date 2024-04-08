package com.github.kejn.rewardcollector.calculator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

@Service
@RequiredArgsConstructor
@CommonsLog
class PropertiesRewardPointsTresholdsProvider implements RewardPointsTresholdsProvider {

	protected static final String REWARD_RULE_RANGE_START = "reward.rule.%s.range.start";
	protected static final String REWARD_RULE_RANGE_END = "reward.rule.%s.range.end";
	protected static final String REWARD_RULE_MULTIPLIER = "reward.rule.%s.multiplier";

	private static final BigDecimal RANGE_END_UNBOUND = new BigDecimal(Long.MAX_VALUE);

	private Map<Range<BigDecimal>, Integer> rangesMap = new HashMap<>();

	@Value("${reward.rule.include}")
	private String[] includedRules;

	private final Environment environment;

	@Override
	public Map<Range<BigDecimal>, Integer> getRangesMap() {
		return rangesMap;
	}

	@PostConstruct
	public void initializeRangesMap() {
		Map<Range<BigDecimal>, Integer> localRangesMap = new HashMap<>();
		for (String ruleIdentifier : includedRules) {
			var rule = createRule(ruleIdentifier);
			if (rule != null) {
				localRangesMap.put(rule.getKey(), rule.getValue());
			}
		}

		if (localRangesMap.isEmpty()) {
			log.warn(
					"Could not find any valid rule configurations. This might be intentional, but please double check configuration files.");
		} else {
			log.debug("Loaded ranges map for reward points configuration: " + localRangesMap);
		}

		rangesMap = Collections.unmodifiableMap(localRangesMap);
	}

	protected Pair<Range<BigDecimal>, Integer> createRule(String ruleIdentifier) {
		try {
			var range = readRange(ruleIdentifier);
			var multiplier = readMultiplier(ruleIdentifier);

			return Pair.of(range, multiplier);
		} catch (IllegalStateException e) {
			log.warn("Skipping invalid rule definition [" + ruleIdentifier
					+ "]. Missing mandatory configuration property. " + e.getMessage());
		}

		return null;
	}

	protected Range<BigDecimal> readRange(String ruleIdentifier) {
		String rangeStartKey = propertyName(REWARD_RULE_RANGE_START, ruleIdentifier);
		BigDecimal rangeStart = environment.getRequiredProperty(rangeStartKey, BigDecimal.class);

		String rangeEndKey = propertyName(REWARD_RULE_RANGE_END, ruleIdentifier);
		BigDecimal rangeEnd = environment.getProperty(rangeEndKey, BigDecimal.class, RANGE_END_UNBOUND);

		return Range.of(rangeStart, rangeEnd);
	}

	protected String propertyName(String propertyKeyTemplate, String ruleIdentifier) {
		return String.format(propertyKeyTemplate, ruleIdentifier);
	}

	protected Integer readMultiplier(String ruleIdentifier) {
		var multiplierKey = propertyName(REWARD_RULE_MULTIPLIER, ruleIdentifier);
		return environment.getRequiredProperty(multiplierKey, Integer.class);
	}

}
