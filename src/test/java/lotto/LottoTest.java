package lotto;

import lotto.model.LottoNumbers;
import lotto.model.LottoNumberGenerator;
import lotto.model.LottoResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LottoTest {

    @Test
    @DisplayName("로또 번호 개수는 6개이면 정상")
    public void test_number_6() {
        assertDoesNotThrow(() ->
            new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6))
        );
    }

    @Test
    @DisplayName("로또 번호 개수는 5개가 될 수 없음")
    public void test_number_5() {
        assertThrows(IllegalArgumentException.class, () ->
            new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5))
        );
    }

    @Test
    @DisplayName("로또 번호 개수는 7개가 될 수 없음")
    public void test_number_7() {
        assertThrows(IllegalArgumentException.class, () ->
            new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6, 7))
        );
    }

    @Test
    @DisplayName("로또 번호가 비어있을 수 없음")
    public void test_number_0() {
        assertThrows(IllegalArgumentException.class, () ->
            new LottoNumbers(new ArrayList<>())
        );
    }

    @Test
    @DisplayName("로또 번호가 범위를 벗어날 수 없음 (1 미만)")
    public void test_range_lower() {
        assertThrows(IllegalArgumentException.class, () ->
            new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 0))
        );
    }

    @Test
    @DisplayName("로또 번호가 범위를 벗어날 수 없음 (45 초과)")
    public void test_range_upper() {
        assertThrows(IllegalArgumentException.class, () ->
            new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 46))
        );
    }

    @Test
    @DisplayName("로또 번호가 중복될 수 없음")
    public void test_duplicated() {
        assertThrows(IllegalArgumentException.class, () ->
            new LottoNumbers(Arrays.asList(1, 1, 2, 3, 4, 5))
        );
    }

    @Test
    @DisplayName("보너스 번호를 포함한 생성자")
    public void test_bonus() {
        LottoNumbers nums = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 7);
        assertEquals(7, nums.getBonus());
    }

    @Test
    @DisplayName("보너스 번호가 없는 생성자")
    public void test_no_bonus() {
        LottoNumbers nums = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertEquals(0, nums.getBonus());
    }

    @Test
    @DisplayName("보너스 번호의 범위는 1부터 45까지 이어야 함")
    public void test_bonus_range() {
        assertThrows(IllegalArgumentException.class, () ->
            new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 0)
        );
    }

    @Test
    @DisplayName("비교 - 6개 일치하는 경우 1등")
    public void test_compare_6() {
        LottoNumbers nums1 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        LottoNumbers nums2 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 7);
        LottoResult result = nums1.compare(nums2);
        assertEquals(LottoResult.RANK_FIRST, result);
    }

    @Test
    @DisplayName("비교 - 5개 일치 + 보너스 일치하는 경우 2등")
    public void test_compare_5_bonus() {
        LottoNumbers nums1 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 7));
        LottoNumbers nums2 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 7);
        LottoResult result = nums1.compare(nums2);
        assertEquals(LottoResult.RANK_SECOND, result);
    }

    @Test
    @DisplayName("비교 - 5개 일치하는 경우 3등")
    public void test_compare_5() {
        LottoNumbers nums1 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 8));
        LottoNumbers nums2 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 7);
        LottoResult result = nums1.compare(nums2);
        assertEquals(LottoResult.RANK_THIRD, result);
    }

    @Test
    @DisplayName("비교 - 4개 일치하는 경우 4등")
    public void test_compare_4() {
        LottoNumbers nums1 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 8, 9));
        LottoNumbers nums2 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 7);
        LottoResult result = nums1.compare(nums2);
        assertEquals(LottoResult.RANK_FOURTH, result);
    }

    @Test
    @DisplayName("비교 - 3개 일치하는 경우 5등")
    public void test_compare_3() {
        LottoNumbers nums1 = new LottoNumbers(Arrays.asList(1, 2, 3, 8, 9, 10));
        LottoNumbers nums2 = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 7);
        LottoResult result = nums1.compare(nums2);
        assertEquals(LottoResult.RANK_FIFTH, result);
    }

    @Test
    @DisplayName("보너스 번호")
    public void test_compare_no_bonus() {
        assertThrows(IllegalArgumentException.class, () ->
            new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), 0)
        );
    }

    @Test
    @DisplayName("로또 번호 생성 - 1~45 범위의 중복 없는 6개 숫자")
    public void test_generate_single_lotto() {
        LottoNumberGenerator generator = new LottoNumberGenerator();

        LottoNumbers generatedLottoNumbers = generator.generate();
        List<Integer> generatedNumbers = generatedLottoNumbers.getNumbers();

        assertEquals(6, generatedNumbers.size());
        assertEquals(6, new HashSet<>(generatedNumbers).size());
        for (Integer generatedNumber : generatedNumbers) {
            assertTrue(generatedNumber >= 1 && generatedNumber <= 45);
        }
    }

    @Test
    @DisplayName("로또 번호 생성 - 원하는 개수만큼 생성")
    public void test_generate_multiple_lotto() {
        LottoNumberGenerator generator = new LottoNumberGenerator();

        List<LottoNumbers> generatedLottos = generator.generate(5);

        assertEquals(5, generatedLottos.size());
    }

    @Test
    @DisplayName("로또 번호 생성 - 생성 개수는 1 이상")
    public void test_generate_invalid_count() {
        LottoNumberGenerator generator = new LottoNumberGenerator();

        assertThrows(IllegalArgumentException.class, () ->
            generator.generate(0)
        );
    }

    @Test
    @DisplayName("로또 번호 생성 - seed 0이면 결과가 재현 가능")
    public void test_generate_seed_zero_reproducible() {
        LottoNumberGenerator firstGenerator = new LottoNumberGenerator(0L);
        LottoNumberGenerator secondGenerator = new LottoNumberGenerator(0L);

        for (int index = 0; index < 3; index++) {
            List<Integer> firstGeneratedNumbers = firstGenerator.generate().getNumbers();
            List<Integer> secondGeneratedNumbers = secondGenerator.generate().getNumbers();

            assertEquals(firstGeneratedNumbers, secondGeneratedNumbers);
            assertEquals(6, firstGeneratedNumbers.size());
            assertEquals(6, new HashSet<>(firstGeneratedNumbers).size());
            for (Integer generatedNumber : firstGeneratedNumbers) {
                assertTrue(generatedNumber >= 1 && generatedNumber <= 45);
            }
        }
    }
}

