package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LottoNumberTest {

    @Test
    @DisplayName("로또 번호는 1과 45가 허용된다")
    void test_valid_boundaries() {
        assertDoesNotThrow(() -> new LottoNumber(1));
        assertDoesNotThrow(() -> new LottoNumber(45));
    }

    @Test
    @DisplayName("로또 번호는 1 미만이면 예외")
    void test_invalid_lower_bound() {
        assertThrows(IllegalArgumentException.class, () -> new LottoNumber(0));
    }

    @Test
    @DisplayName("로또 번호는 45 초과이면 예외")
    void test_invalid_upper_bound() {
        assertThrows(IllegalArgumentException.class, () -> new LottoNumber(46));
    }
}
