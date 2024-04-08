package com.github.kejn.rewardcollector.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseVO {
	private Long id;
	private String userIdentifier;
	private BigDecimal transactionValue;
	private LocalDateTime placedAt;
	
}
