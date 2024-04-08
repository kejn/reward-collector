package com.github.kejn.rewardcollector.calculator;

import static com.github.kejn.rewardcollector.calculator.PropertiesRewardPointsTresholdsProvider.REWARD_RULE_MULTIPLIER;
import static com.github.kejn.rewardcollector.calculator.PropertiesRewardPointsTresholdsProvider.REWARD_RULE_RANGE_END;
import static com.github.kejn.rewardcollector.calculator.PropertiesRewardPointsTresholdsProvider.REWARD_RULE_RANGE_START;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import com.github.kejn.rewardcollector.tag.TestTag;

@Tag(TestTag.UNIT)
@ExtendWith(MockitoExtension.class)
class PropertiesRewardPointsTresholdsProviderTest {

	private static final String SAMPLE_RULE_IDENTIFIER = "someRuleId";

	@Mock
	private Environment environment;

	@InjectMocks
	private PropertiesRewardPointsTresholdsProvider provider;

	@Test
	void shouldThrowExceptionWhenRangeStartIsMissing() {
		// given
		String rangeStartProperty = provider.propertyName(REWARD_RULE_RANGE_START, SAMPLE_RULE_IDENTIFIER);
		assumeTrue(isNotBlank(rangeStartProperty));

		// when
		when(environment.getRequiredProperty(eq(rangeStartProperty), any())).thenThrow(IllegalStateException.class);

		// then
		assertThrows(IllegalStateException.class, () -> {
			provider.readRange(SAMPLE_RULE_IDENTIFIER);
		});

		verify(environment).getRequiredProperty(eq(rangeStartProperty), any());
	}

	@Test
	void shouldThrowExceptionWhenMultiplierIsMissing() {
		// given
		String multiplierProperty = provider.propertyName(REWARD_RULE_MULTIPLIER, SAMPLE_RULE_IDENTIFIER);
		assumeTrue(isNotBlank(multiplierProperty));

		// when
		when(environment.getRequiredProperty(eq(multiplierProperty), any())).thenThrow(IllegalStateException.class);

		// then
		assertThrows(IllegalStateException.class, () -> {
			provider.readMultiplier(SAMPLE_RULE_IDENTIFIER);
		});

		verify(environment).getRequiredProperty(eq(multiplierProperty), any());
	}

	@Test
	void rangeEndShouldBeOptional() throws Exception {
		// given
		String rangeStartProperty = provider.propertyName(REWARD_RULE_RANGE_START, SAMPLE_RULE_IDENTIFIER);
		String rangeEndProperty = provider.propertyName(REWARD_RULE_RANGE_END, SAMPLE_RULE_IDENTIFIER);
		
		assumeTrue(isNotBlank(rangeStartProperty));
		assumeTrue(isNotBlank(rangeEndProperty));

		// when
		when(environment.getRequiredProperty(eq(rangeStartProperty), any())).thenReturn(BigDecimal.ZERO);
		when(environment.getProperty(eq(rangeEndProperty), any(), any())).thenAnswer(AdditionalAnswers.returnsLastArg());

		provider.readRange(SAMPLE_RULE_IDENTIFIER);

		// then
		verify(environment).getRequiredProperty(eq(rangeStartProperty), any());
		verify(environment).getProperty(eq(rangeEndProperty), any(), any());
	}
}
