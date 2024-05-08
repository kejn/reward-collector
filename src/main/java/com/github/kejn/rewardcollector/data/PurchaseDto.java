package com.github.kejn.rewardcollector.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
	
	@NotBlank
	private String userIdentifier;
	
	@NotBlank
	private BigDecimal transactionValue;
	
	@NotBlank
	private LocalDateTime placedAt;
}
