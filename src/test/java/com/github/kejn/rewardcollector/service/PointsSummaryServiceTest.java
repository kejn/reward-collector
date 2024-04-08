package com.github.kejn.rewardcollector.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Random;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.kejn.rewardcollector.calculator.TresholdRewardPointsCalculator;
import com.github.kejn.rewardcollector.data.PurchaseVO;
import com.github.kejn.rewardcollector.tag.TestTag;

@Tag(TestTag.UNIT)
@ExtendWith(MockitoExtension.class)
class PointsSummaryServiceTest {
	private static final int YEAR = 2024;

	private static final BigDecimal FIRST_TRANSACTION_VALUE = new BigDecimal(55);
	private static final BigDecimal ANOTHER_TRANSACTION_VALUE = new BigDecimal(111);

	@Mock
	private TresholdRewardPointsCalculator calculator;

	@InjectMocks
	private PointsSummaryService service;

	@Test
	void shouldAddPointsFromTwoTransactions() {
		// given
		var first = PurchaseVO.builder().transactionValue(FIRST_TRANSACTION_VALUE).build();
		var second = PurchaseVO.builder().transactionValue(ANOTHER_TRANSACTION_VALUE).build();

		List<PurchaseVO> transactions = Lists.list(first, second);

		// when
		service.totalPoints(transactions);

		// then
		verify(calculator).calculatePoints(eq(FIRST_TRANSACTION_VALUE));
		verify(calculator).calculatePoints(eq(ANOTHER_TRANSACTION_VALUE));
	}

	@Test
	void shouldAddPointsFromGivenMonthOnly() {
		// given
		YearMonth selectedMonth = YearMonth.of(YEAR, Month.MARCH.getValue());

		var excludedDate = sampleDateTime(YearMonth.of(YEAR, Month.FEBRUARY.getValue()));
		var secondDate = sampleDateTime(selectedMonth);
		var thirdDate = sampleDateTime(selectedMonth);

		var excluded = PurchaseVO.builder().transactionValue(FIRST_TRANSACTION_VALUE).placedAt(excludedDate).build();
		var second = PurchaseVO.builder().transactionValue(ANOTHER_TRANSACTION_VALUE).placedAt(secondDate).build();
		var third = PurchaseVO.builder().transactionValue(ANOTHER_TRANSACTION_VALUE).placedAt(thirdDate).build();

		List<PurchaseVO> transactions = Lists.list(excluded, second, third);

		// when
		service.pointsMonthly(selectedMonth, transactions);

		// then
		verify(calculator, times(2)).calculatePoints(eq(ANOTHER_TRANSACTION_VALUE));
	}

	@Test
	void shouldFindNoPointsInTheFuture() {
		// given
		YearMonth selectedMonth = YearMonth.of(YEAR, Month.AUGUST.getValue());
		YearMonth actualMonth = YearMonth.of(YEAR, Month.APRIL.getValue());

		var firstDate = sampleDateTime(actualMonth);
		var secondDate = sampleDateTime(actualMonth);
		var thirdDate = sampleDateTime(actualMonth);

		var excluded = PurchaseVO.builder().transactionValue(FIRST_TRANSACTION_VALUE).placedAt(firstDate).build();
		var second = PurchaseVO.builder().transactionValue(ANOTHER_TRANSACTION_VALUE).placedAt(secondDate).build();
		var third = PurchaseVO.builder().transactionValue(ANOTHER_TRANSACTION_VALUE).placedAt(thirdDate).build();

		long noPointsGranted = 0L;

		// when
		long totalPoints = service.pointsMonthly(selectedMonth, Lists.list(excluded, second, third));

		// then
		assertThat(totalPoints).isEqualTo(noPointsGranted);

		verify(calculator, never()).calculatePoints(any());
	}

	private LocalDateTime sampleDateTime(YearMonth selectedMonth) {
		var random = new Random();
		return LocalDate.of(selectedMonth.getYear(), selectedMonth.getMonthValue(),
				selectedMonth.atDay(random.nextInt(20) + 1).getDayOfMonth()).atStartOfDay();
	}

}
