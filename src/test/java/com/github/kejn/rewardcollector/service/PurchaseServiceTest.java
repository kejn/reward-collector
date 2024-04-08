package com.github.kejn.rewardcollector.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.github.kejn.rewardcollector.data.PurchaseDto;
import com.github.kejn.rewardcollector.db.Purchase;
import com.github.kejn.rewardcollector.db.PurchaseRepository;
import com.github.kejn.rewardcollector.tag.TestTag;

@Tag(TestTag.UNIT)
@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

	private static final String SOME_USER_ID = "someUserId";

	private ModelMapper mapper = new ModelMapper();

	private PurchaseRepository repository;

	private PurchaseService service;

	@BeforeEach
	public void setUp() {
		repository = mock(PurchaseRepository.class);
		service = new PurchaseService(repository, mapper);
	}

	@Test
	void shouldSaveTransactionToRepository() {
		// given
		PurchaseDto dto = PurchaseDto.builder().userIdentifier(SOME_USER_ID).transactionValue(new BigDecimal(33))
				.placedAt(LocalDateTime.now()).build();
		Purchase model = mapper.map(dto, Purchase.class);

		// when
		service.registerPurchase(dto);

		// then
		verify(repository).save(eq(model));
	}
	
	@Test
	void shouldFindAllByUserIdentifier() {
		// when
		service.findAllByUserIdentifier(SOME_USER_ID);
		
		// then
		verify(repository).findByUserIdentifier(eq(SOME_USER_ID));
	}
	
	@Test
	void shouldDeleteByUserIdentifier() {
		// when
		service.deleteByUserIdentifier(SOME_USER_ID);
		
		// then
		verify(repository).deleteByUserIdentifier(eq(SOME_USER_ID));
	}
	
	@Test
	void shouldDeleteAllPurchases() {
		// when
		service.purge();
		
		// then
		verify(repository).deleteAll();
	}
}
