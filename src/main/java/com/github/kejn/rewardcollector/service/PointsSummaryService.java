package com.github.kejn.rewardcollector.service;

import java.time.YearMonth;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

import org.springframework.stereotype.Service;

import com.github.kejn.rewardcollector.calculator.RewardPointsCalculator;
import com.github.kejn.rewardcollector.data.PurchaseVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointsSummaryService {

	private final RewardPointsCalculator calculator;

	public long pointsMonthly(YearMonth selectedMonth, List<PurchaseVO> transactions) {
		return transactions.stream() //
				.filter(matchingSelectedMonth(selectedMonth)) //
				.mapToLong(calculatePointsOfSingleTransaction()) //
				.sum();
	}

	private Predicate<? super PurchaseVO> matchingSelectedMonth(YearMonth selectedMonth) {
		return transaction -> selectedMonth.getYear() == transaction.getPlacedAt().getYear()
				&& selectedMonth.getMonthValue() == transaction.getPlacedAt().getMonthValue();
	}

	public long totalPoints(List<PurchaseVO> transactionStream) {
		return transactionStream.stream().mapToLong(calculatePointsOfSingleTransaction()).sum();
	}

	private ToLongFunction<? super PurchaseVO> calculatePointsOfSingleTransaction() {
		return t -> calculator.calculatePoints(t.getTransactionValue());
	}

}
