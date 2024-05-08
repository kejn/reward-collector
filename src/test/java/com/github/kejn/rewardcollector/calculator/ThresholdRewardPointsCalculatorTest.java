package com.github.kejn.rewardcollector.calculator;

import org.junit.jupiter.api.Tag;

import com.github.kejn.rewardcollector.tag.TestTag;

@Tag(TestTag.UNIT)
class ThresholdRewardPointsCalculatorTest extends AbstractRewardPointsCalculatorTest {

	private final RewardPointsCalculator calculator;
	
	public ThresholdRewardPointsCalculatorTest() {
		var thresholdsProvider = new HardcodedRewardPointsThresholdsProvider();
		calculator = new ThresholdRewardPointsCalculator(thresholdsProvider);
	}
	
	@Override
	protected RewardPointsCalculator getCalculator() {
		return calculator;
	}
}
