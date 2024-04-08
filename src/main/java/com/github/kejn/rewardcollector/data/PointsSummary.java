package com.github.kejn.rewardcollector.data;

import java.time.YearMonth;

public record PointsSummary(String userIdentifier, long rewardPoints, YearMonth period) {
}
