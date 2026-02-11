package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LottoNumbersCompareTest {

    @Test
    @DisplayName("당첨 번호의 보너스 번호는 당첨 번호와 중복될 수 없다")
    void test_winning_bonus_duplicate() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new WinningLottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 6)
        );
    }

    @Test
    @DisplayName("당첨 번호와 구매 번호를 비교해 2등을 계산한다")
    void test_compare_rank_second() {
        WinningLottoNumbers winning = new WinningLottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 7);
        PurchasedLottoNumbers purchased = new PurchasedLottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 7));

        assertEquals(LottoResult.RANK_SECOND, winning.compare(purchased));
    }
}
