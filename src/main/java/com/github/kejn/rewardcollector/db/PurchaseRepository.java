package com.github.kejn.rewardcollector.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

	public List<Purchase> findByUserIdentifier(String userIdentifier);

	public long deleteByUserIdentifier(String userIdentifier);

}
