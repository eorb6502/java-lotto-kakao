package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LottoNumbersTest {

    @Test
    @DisplayName("로또 번호는 생성 시 오름차순으로 정렬된다")
    void test_numbers_are_sorted() {
        LottoNumbers lottoNumbers = new LottoNumbers(List.of(6, 1, 5, 2, 4, 3));
        assertEquals(List.of(1, 2, 3, 4, 5, 6), lottoNumbers.getNumbers());
    }

    @Test
    @DisplayName("로또 번호 목록이 null이면 예외가 발생한다")
    void test_numbers_null() {
        assertThrows(IllegalArgumentException.class, () -> new LottoNumbers(null));
    }

    @Test
    @DisplayName("로또 번호에 null 원소가 포함되면 예외가 발생한다")
    void test_numbers_contains_null_element() {
        assertThrows(IllegalArgumentException.class, () -> new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, null)));
    }

    @Test
    @DisplayName("비교 대상 로또 번호가 null이면 예외가 발생한다")
    void test_count_matches_with_null_other() {
        LottoNumbers lottoNumbers = new LottoNumbers(List.of(1, 2, 3, 4, 5, 6));
        assertThrows(IllegalArgumentException.class, () -> lottoNumbers.countMatches(null));
    }
}
