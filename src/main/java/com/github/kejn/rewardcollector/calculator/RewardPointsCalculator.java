package com.github.kejn.rewardcollector.calculator;

import java.math.BigDecimal;

public interface RewardPointsCalculator {

	long calculatePoints(BigDecimal transactionValue);

}