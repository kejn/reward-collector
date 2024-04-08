package com.github.kejn.rewardcollector.calculator;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.kejn.rewardcollector.tag.TestTag;

import lombok.RequiredArgsConstructor;

@Tag(TestTag.INTEGRATION)
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
class RewardPointsCalculatorIntegrationTest extends AbstractRewardPointsCalculatorTest {

	private final RewardPointsCalculator calculator;

	@Override
	protected RewardPointsCalculator getCalculator() {
		return calculator;
	}
}
