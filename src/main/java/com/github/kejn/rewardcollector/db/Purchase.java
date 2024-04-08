package com.github.kejn.rewardcollector.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Purchase {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String userIdentifier;
	
	private BigDecimal transactionValue;
	
	private LocalDateTime placedAt;
	
	
}
