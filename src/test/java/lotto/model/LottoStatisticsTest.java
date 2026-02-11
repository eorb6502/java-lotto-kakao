package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LottoStatisticsTest {

    @Test
    @DisplayName("당첨 결과 집계와 수익률을 계산한다")
    void test_create_statistics() {
        WinningLottoNumbers winningNumber = new WinningLottoNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        List<PurchasedLottoNumbers> purchasedNumbers = List.of(
            new PurchasedLottoNumbers(List.of(1, 2, 3, 4, 5, 6)),
            new PurchasedLottoNumbers(List.of(1, 2, 3, 4, 5, 7)),
            new PurchasedLottoNumbers(List.of(1, 2, 3, 4, 5, 8)),
            new PurchasedLottoNumbers(List.of(1, 2, 3, 4, 9, 10)),
            new PurchasedLottoNumbers(List.of(1, 2, 3, 11, 12, 13)),
            new PurchasedLottoNumbers(List.of(8, 9, 10, 11, 12, 13))
        );

        LottoStatistics statistics = LottoStatistics.from(winningNumber, purchasedNumbers, 6000);

        assertEquals(1, statistics.resultCountByRank().getOrDefault(LottoResult.RANK_FIRST, 0));
        assertEquals(1, statistics.resultCountByRank().getOrDefault(LottoResult.RANK_SECOND, 0));
        assertEquals(1, statistics.resultCountByRank().getOrDefault(LottoResult.RANK_THIRD, 0));
        assertEquals(1, statistics.resultCountByRank().getOrDefault(LottoResult.RANK_FOURTH, 0));
        assertEquals(1, statistics.resultCountByRank().getOrDefault(LottoResult.RANK_FIFTH, 0));
        assertEquals(1, statistics.resultCountByRank().getOrDefault(LottoResult.RANK_NONE, 0));
        assertEquals(338592.5, statistics.profitRate(), 0.00001);
    }
}
