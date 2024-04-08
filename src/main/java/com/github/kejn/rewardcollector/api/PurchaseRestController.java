package com.github.kejn.rewardcollector.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.kejn.rewardcollector.data.PurchaseDto;
import com.github.kejn.rewardcollector.data.PurchaseVO;
import com.github.kejn.rewardcollector.service.PurchaseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(PurchaseRestController.ROOT_PATH)
public class PurchaseRestController {

	static final String ROOT_PATH = "purchase";

	private final PurchaseService service;

	@PostMapping
	public ResponseEntity<Object> registerPurchase(@RequestBody @NotEmpty @Valid PurchaseDto purchase) {
		service.registerPurchase(purchase);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("{userIdentifier}")
	public List<PurchaseVO> findAllRegisteredTransactionsByUserIdentifier(
			@PathVariable("userIdentifier") @NotEmpty @Valid String userIdentifier) {
		List<PurchaseVO> allByUserIdentifier = service.findAllByUserIdentifier(userIdentifier);
		return allByUserIdentifier;
	}

	@DeleteMapping("{userIdentifier}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllRegisteredTransactionsByUserIdentifier(
			@PathVariable("userIdentifier") @NotEmpty @Valid String userIdentifier) {
		service.deleteByUserIdentifier(userIdentifier);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllRegisteredTransactions() {
		service.purge();
	}

}
