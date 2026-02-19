package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LottoResultTest {

    @Test
    @DisplayName("통계 출력 대상 등수는 5등부터 1등 순서다")
    void test_statistics_results_order() {
        assertEquals(
            List.of(
                LottoResult.RANK_FIFTH,
                LottoResult.RANK_FOURTH,
                LottoResult.RANK_THIRD,
                LottoResult.RANK_SECOND,
                LottoResult.RANK_FIRST
            ),
            LottoResult.statisticsResults()
        );
    }

    @Test
    @DisplayName("낙첨은 통계 출력 포맷을 가지지 않는다")
    void test_rank_none_has_no_statistics_format() {
        assertThrows(IllegalArgumentException.class, LottoResult.RANK_NONE::getStatisticsFormat);
    }
}
