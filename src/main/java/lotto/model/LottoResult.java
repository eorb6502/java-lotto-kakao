package lotto.model;

public enum LottoResult {
    RANK_FIRST (2_000_000_000L),
    RANK_SECOND(30_000_000L),
    RANK_THIRD (1_500_000L),
    RANK_FOURTH(50_000L),
    RANK_FIFTH (5_000L),
    RANK_NONE  (0L);

    private final long prize;

    LottoResult(long prize) {
        this.prize = prize;
    }

    public long getPrize() {
        return prize;
    }
}
