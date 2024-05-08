package com.github.kejn.rewardcollector.api;

import java.time.YearMonth;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.kejn.rewardcollector.data.PointsSummary;
import com.github.kejn.rewardcollector.data.PurchaseVO;
import com.github.kejn.rewardcollector.service.PointsSummaryService;
import com.github.kejn.rewardcollector.service.PurchaseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(RewardPointsRestController.ROOT_PATH)
public class RewardPointsRestController {

	static final String ROOT_PATH = "reward-points";

	private final PurchaseService purchaseService;

	private final PointsSummaryService pointsSummaryService;

	@GetMapping("{userIdentifier}/total")
	public PointsSummary getUserTotalPoints(@PathVariable("userIdentifier") @NotEmpty @Valid String userIdentifier) {
		List<PurchaseVO> allByUserIdentifier = purchaseService.findAllByUserIdentifier(userIdentifier);

		long totalPoints = pointsSummaryService.totalPoints(allByUserIdentifier);

		return new PointsSummary(userIdentifier, totalPoints, null);
	}

	@GetMapping("{userIdentifier}/monthly")
	public PointsSummary getUserMonthlyPoints(@PathVariable("userIdentifier") @NotEmpty String userIdentifier,
			@RequestParam("monthsBack") @Pattern(regexp = "[0-3]") String monthsBack) {
		List<PurchaseVO> allByUserIdentifier = purchaseService.findAllByUserIdentifier(userIdentifier);

		YearMonth selectedMonth = YearMonth.now().minusMonths(Long.valueOf(monthsBack));
		long totalPoints = pointsSummaryService.pointsMonthly(selectedMonth, allByUserIdentifier);

		return new PointsSummary(userIdentifier, totalPoints, selectedMonth);
	}
}
