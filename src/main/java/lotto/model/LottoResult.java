package lotto.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum LottoResult {
    RANK_FIRST (2_000_000_000L, 6, null),
    RANK_SECOND(30_000_000L, 5, true),
    RANK_THIRD (1_500_000L, 5, false),
    RANK_FOURTH(50_000L, 4, null),
    RANK_FIFTH (5_000L, 3, null),
    RANK_NONE  (0L, -1, null);

    private static final Map<MatchKey, LottoResult> RESULT_BY_MATCH_KEY = Arrays.stream(values())
        .filter(result -> result != RANK_NONE)
        .collect(Collectors.toUnmodifiableMap(
            result -> new MatchKey(result.matchCount, result.bonusMatched),
            result -> result
        ));

    private final long prize;
    private final int matchCount;
    private final Boolean bonusMatched;

    LottoResult(long prize, int matchCount, Boolean bonusMatched) {
        this.prize = prize;
        this.matchCount = matchCount;
        this.bonusMatched = bonusMatched;
    }

    public long getPrize() {
        return prize;
    }

    public static LottoResult from(int matchCount, boolean bonusMatched) {
        return Optional.ofNullable(RESULT_BY_MATCH_KEY.get(new MatchKey(matchCount, bonusMatched)))
            .orElse(RESULT_BY_MATCH_KEY.getOrDefault(new MatchKey(matchCount, null), RANK_NONE));
    }

    private record MatchKey(int matchCount, Boolean bonusMatched) { }
}
