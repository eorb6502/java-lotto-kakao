package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LottoNumberGeneratorTest {

    @Test
    @DisplayName("로또 번호 생성 개수는 0 이상이어야 한다")
    void test_generate_negative_count() {
        LottoNumberGenerator generator = new LottoNumberGenerator();
        assertThrows(IllegalArgumentException.class, () -> generator.generate(-1));
    }
}
