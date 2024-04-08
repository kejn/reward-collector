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
public class TresholdRewardPointsCalculator implements RewardPointsCalculator {

	private final RewardPointsTresholdsProvider tresholdsProvider;

	@Override
	public long calculatePoints(BigDecimal transactionValue) {
		Objects.requireNonNull(transactionValue);

		long result = tresholdsProvider.getRangesMap().entrySet().stream()//
				.filter(transactionPassingTreshold(transactionValue))//
				.reduce(0L, pointsGranted(transactionValue), Long::sum);
		return result;
	}

	private Predicate<? super Entry<Range<BigDecimal>, Integer>> transactionPassingTreshold(
			BigDecimal transactionValue) {
		return mapEntry -> {
			Range<BigDecimal> rewardRange = mapEntry.getKey();
			Range<BigDecimal> transactionRange = Range.of(BigDecimal.ZERO, transactionValue);
			return rewardRange.isOverlappedBy(transactionRange) && !rewardRange.isStartedBy(transactionValue);
		};
	}

	private BiFunction<Long, Entry<Range<BigDecimal>, Integer>, Long> pointsGranted(BigDecimal transactionValue) {
		return (currentPoints, e) -> {
			BigDecimal treshold = e.getKey().getMinimum();
			BigDecimal maxAmountCounting = e.getKey().getMaximum().min(transactionValue);
			Integer multiplier = e.getValue();
			return currentPoints + maxAmountCounting.subtract(treshold).max(BigDecimal.ZERO).longValue() * multiplier;
		};
	}

}
