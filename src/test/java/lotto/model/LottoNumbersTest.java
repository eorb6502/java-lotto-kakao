package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LottoNumbersTest {

    @Test
    @DisplayName("로또 번호는 생성 시 오름차순으로 정렬된다")
    void test_numbers_are_sorted() {
        LottoNumbers lottoNumbers = new LottoNumbers(List.of(6, 1, 5, 2, 4, 3));
        assertEquals(List.of(1, 2, 3, 4, 5, 6), lottoNumbers.getNumbers());
    }
}
