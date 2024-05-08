package com.github.kejn.rewardcollector.calculator;

import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThresholdRewardPointsCalculator implements RewardPointsCalculator {

	private final RewardPointsThresholdsProvider thresholdsProvider;

	@Override
	public long calculatePoints(BigDecimal transactionValue) {
		Objects.requireNonNull(transactionValue);

		long result = thresholdsProvider.getRangesMap().entrySet().stream()//
				.filter(transactionPassingThreshold(transactionValue))//
				.reduce(0L, pointsGranted(transactionValue), Long::sum);
		return result;
	}

	private Predicate<? super Entry<Range<BigDecimal>, Integer>> transactionPassingThreshold(
			BigDecimal transactionValue) {
		return mapEntry -> {
			Range<BigDecimal> rewardRange = mapEntry.getKey();
			Range<BigDecimal> transactionRange = Range.of(BigDecimal.ZERO, transactionValue);
			return rewardRange.isOverlappedBy(transactionRange) && !rewardRange.isStartedBy(transactionValue);
		};
	}

	private BiFunction<Long, Entry<Range<BigDecimal>, Integer>, Long> pointsGranted(BigDecimal transactionValue) {
		return (currentPoints, e) -> {
			BigDecimal threshold = e.getKey().getMinimum();
			BigDecimal maxAmountCounting = e.getKey().getMaximum().min(transactionValue);
			Integer multiplier = e.getValue();
			return currentPoints + maxAmountCounting.subtract(threshold).max(BigDecimal.ZERO).longValue() * multiplier;
		};
	}

}
