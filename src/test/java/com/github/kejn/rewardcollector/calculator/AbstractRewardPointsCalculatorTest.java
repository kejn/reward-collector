package com.github.kejn.rewardcollector.calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

abstract class AbstractRewardPointsCalculatorTest {

	protected abstract RewardPointsCalculator getCalculator();

	private void testCalculator(BigDecimal transactionValue, long expectedResult) {
		// when
		long points = getCalculator().calculatePoints(transactionValue);

		// then
		assertThat(points).isEqualTo(expectedResult);
	}

	@Test
	void shouldGrantNoPoints() {
		testCalculator(BigDecimal.ZERO, NumberUtils.LONG_ZERO);
	}

	@Test
	void shouldGrant1PointForEveryDollarOver50() {
		testCalculator(new BigDecimal(55), 5L); // 1*5
	}

	@Test
	void shouldGrant2PointsForEveryDollarOver100() {
		testCalculator(new BigDecimal(144), 138L); // 2*44 + 1*50
	}

	@Test
	void shouldGrantNoPointsForNegativeInput() {
		testCalculator(new BigDecimal(-1), NumberUtils.LONG_ZERO);
	}

	@Test
	void shouldHandleEnormousInputs() {
		// given 
		BigDecimal largeTransaction = new BigDecimal(Integer.MAX_VALUE);
		
		//when
		long points = getCalculator().calculatePoints(largeTransaction);
		
		// then
		assertThat(points).isGreaterThan(NumberUtils.LONG_ZERO);
		
	}
	
	@Test
	void shouldNotAllowToPassInNullArgument() {
		assertThrows(NullPointerException.class, () -> {
			getCalculator().calculatePoints(null);
		});
	}
}
