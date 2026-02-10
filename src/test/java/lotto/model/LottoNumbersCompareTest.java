package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LottoNumbersCompareTest {

    @Test
    @DisplayName("두 로또 모두 보너스가 없으면 비교는 예외")
    void test_compare_without_bonus_on_both() {
        LottoNumbers first = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        LottoNumbers second = new LottoNumbers(Arrays.asList(7, 8, 9, 10, 11, 12));
        assertThrows(IllegalArgumentException.class, () -> first.compare(second));
    }

    @Test
    @DisplayName("두 로또 모두 보너스가 있으면 비교는 예외")
    void test_compare_with_bonus_on_both() {
        LottoNumbers first = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 7);
        LottoNumbers second = new LottoNumbers(Arrays.asList(7, 8, 9, 10, 11, 12), 13);
        assertThrows(IllegalArgumentException.class, () -> first.compare(second));
    }
}
