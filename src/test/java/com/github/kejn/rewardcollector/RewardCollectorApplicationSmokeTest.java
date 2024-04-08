package com.github.kejn.rewardcollector;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.kejn.rewardcollector.tag.TestTag;

@Tag(TestTag.INTEGRATION)
@SpringBootTest
class RewardCollectorApplicationSmokeTest {

	@Test
	void shouldStartApplicationWithoutErrors() {
	}

}
