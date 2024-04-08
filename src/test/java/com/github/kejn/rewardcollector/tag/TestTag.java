package com.github.kejn.rewardcollector.tag;

public interface TestTag {

	/**
	 * You can optionally use this to mark unit tests to be more verbose on your
	 * intentions for given test class.
	 */
	String UNIT = "unit";

	/**
	 * Use this to tag integration tests that might be more resource-heavy than unit
	 * tests, so we might not always want to execute them.
	 */
	String INTEGRATION = "integration";

	/**
	 * Use this to tag API tests that might be more resource-heavy than unit tests,
	 * so we might not always want to execute them.
	 */
	String API = "api";
}
