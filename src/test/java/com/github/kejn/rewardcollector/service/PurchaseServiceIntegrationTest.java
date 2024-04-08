package com.github.kejn.rewardcollector.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.github.kejn.rewardcollector.data.PurchaseDto;
import com.github.kejn.rewardcollector.data.PurchaseVO;
import com.github.kejn.rewardcollector.tag.TestTag;

import lombok.RequiredArgsConstructor;

@Tag(TestTag.INTEGRATION)
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
@Transactional
class PurchaseServiceIntegrationTest {

	private static final BigDecimal SOME_TRANSACTION_VALUE = new BigDecimal(33);
	private static final String SOME_USER_ID = "someUserId";
	
	private final PurchaseService service;

	
	@Test
	void shouldSaveTransactionToRepository() {
		// given
		LocalDateTime dateAndTime = LocalDateTime.now();
		PurchaseDto dto = PurchaseDto.builder().userIdentifier(SOME_USER_ID)
				.transactionValue(SOME_TRANSACTION_VALUE)
				.placedAt(dateAndTime).build();
		
		// when
		PurchaseVO result = service.registerPurchase(dto);
		
		// then
		assertThat(result).isNotNull();
		assertThat(result.getUserIdentifier()).isEqualTo(SOME_USER_ID);
		assertThat(result.getTransactionValue()).isEqualTo(SOME_TRANSACTION_VALUE);
		assertThat(result.getPlacedAt()).isEqualTo(dateAndTime);
	}

}
