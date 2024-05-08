package com.github.kejn.rewardcollector.calculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Range;

class HardcodedRewardPointsThresholdsProvider implements RewardPointsThresholdsProvider {

	private static final BigDecimal FIRST_THRESHOLD = new BigDecimal(50);
	private static final BigDecimal SECOND_THRESHOLD = new BigDecimal(100);

	private static final BigDecimal RANGE_END_UNBOUND = new BigDecimal(Long.MAX_VALUE);
	
	private Map<Range<BigDecimal>, Integer> rangesMap = new HashMap<>();
	
	public HardcodedRewardPointsThresholdsProvider() {
		rangesMap.put(Range.of(FIRST_THRESHOLD, SECOND_THRESHOLD), 1);
		rangesMap.put(Range.of(SECOND_THRESHOLD, RANGE_END_UNBOUND), 2);
	}
	
	@Override
	public Map<Range<BigDecimal>, Integer> getRangesMap() {
		return rangesMap;
	}

}
