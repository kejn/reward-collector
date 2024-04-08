package com.github.kejn.rewardcollector.calculator;

import org.junit.jupiter.api.Tag;

import com.github.kejn.rewardcollector.tag.TestTag;

@Tag(TestTag.UNIT)
class TresholdRewardPointsCalculatorTest extends AbstractRewardPointsCalculatorTest {

	private final RewardPointsCalculator calculator;
	
	public TresholdRewardPointsCalculatorTest() {
		var tresholdsProvider = new HardcodedRewardPointsTresholdsProvider();
		calculator = new TresholdRewardPointsCalculator(tresholdsProvider);
	}
	
	@Override
	protected RewardPointsCalculator getCalculator() {
		return calculator;
	}
}
