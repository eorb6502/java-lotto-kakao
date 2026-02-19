package lotto.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum LottoResult {
    RANK_FIRST (2_000_000_000L, 6, null, 5, "6개 일치 (%s)- %d개%n"),
    RANK_SECOND(30_000_000L, 5, true, 4, "5개 일치, 보너스 볼 일치(%s) - %d개%n"),
    RANK_THIRD (1_500_000L, 5, false, 3, "5개 일치 (%s)- %d개%n"),
    RANK_FOURTH(50_000L, 4, null, 2, "4개 일치 (%s)- %d개%n"),
    RANK_FIFTH (5_000L, 3, null, 1, "3개 일치 (%s)- %d개%n"),
    RANK_NONE  (0L, -1, null, 0, null);

    private static final Map<MatchKey, LottoResult> RESULT_BY_MATCH_KEY = Arrays.stream(values())
        .filter(result -> result != RANK_NONE)
        .collect(Collectors.toUnmodifiableMap(
            result -> new MatchKey(result.matchCount, result.bonusMatched),
            result -> result
        ));
    private static final List<LottoResult> STATISTICS_RESULTS = Arrays.stream(values())
        .filter(LottoResult::isDisplayedInStatistics)
        .sorted(Comparator.comparingInt(LottoResult::getStatisticsOrder))
        .toList();

    private final long prize;
    private final int matchCount;
    private final Boolean bonusMatched;
    private final int statisticsOrder;
    private final String statisticsFormat;

    LottoResult(long prize, int matchCount, Boolean bonusMatched, int statisticsOrder, String statisticsFormat) {
        this.prize = prize;
        this.matchCount = matchCount;
        this.bonusMatched = bonusMatched;
        this.statisticsOrder = statisticsOrder;
        this.statisticsFormat = statisticsFormat;
    }

    public long getPrize() {
        return prize;
    }

    public String getStatisticsFormat() {
        if (!isDisplayedInStatistics()) {
            throw new IllegalArgumentException("낙첨은 통계 출력 형식을 가지지 않습니다.");
        }
        return statisticsFormat;
    }

    public static LottoResult from(int matchCount, boolean bonusMatched) {
        return Optional.ofNullable(RESULT_BY_MATCH_KEY.get(new MatchKey(matchCount, bonusMatched)))
            .orElse(RESULT_BY_MATCH_KEY.getOrDefault(new MatchKey(matchCount, null), RANK_NONE));
    }

    public static List<LottoResult> statisticsResults() {
        return STATISTICS_RESULTS;
    }

    private boolean isDisplayedInStatistics() {
        return statisticsFormat != null;
    }

    private int getStatisticsOrder() {
        return statisticsOrder;
    }

    private record MatchKey(int matchCount, Boolean bonusMatched) { }
}
