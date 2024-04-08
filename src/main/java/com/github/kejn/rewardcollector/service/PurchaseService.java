package com.github.kejn.rewardcollector.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kejn.rewardcollector.data.PurchaseDto;
import com.github.kejn.rewardcollector.data.PurchaseVO;
import com.github.kejn.rewardcollector.db.Purchase;
import com.github.kejn.rewardcollector.db.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class PurchaseService {

	private final PurchaseRepository repository;

	private final ModelMapper mapper;

	public PurchaseVO registerPurchase(PurchaseDto purchase) {
		Purchase entity = mapper.map(purchase, Purchase.class);
		entity = repository.save(entity);

		return Optional.ofNullable(entity).map(e -> mapper.map(e, PurchaseVO.class)).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<PurchaseVO> findAllByUserIdentifier(String userIdentifier) {
		return repository.findByUserIdentifier(userIdentifier).stream()
				.map(entity -> mapper.map(entity, PurchaseVO.class)).collect(Collectors.toUnmodifiableList());
	}

	public long deleteByUserIdentifier(String userIdentifier) {
		return repository.deleteByUserIdentifier(userIdentifier);
	}

	public void purge() {
		repository.deleteAll();
	}

}
