package com.github.kejn.rewardcollector.calculator;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.Range;

interface RewardPointsTresholdsProvider {
	/**
	 * key - range of transaction value (exclusive, inclusive]
	 * value - multiplier of transaction value units overlapping with that range
	 * 
	 * @return map of ranges - multipliers
	 */
	Map<Range<BigDecimal>, Integer> getRangesMap();
}
