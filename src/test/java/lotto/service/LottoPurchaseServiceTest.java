package lotto.service;

import lotto.model.LottoNumberGenerator;
import lotto.model.PurchaseResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LottoPurchaseServiceTest {

    @Test
    @DisplayName("수동과 자동 로또를 함께 구매한다")
    void test_purchase_manual_and_auto() {
        LottoPurchaseService service = new LottoPurchaseService(new LottoNumberGenerator(1L));
        List<List<Integer>> manualLottoNumbers = List.of(
            List.of(1, 2, 3, 4, 5, 6),
            List.of(7, 8, 9, 10, 11, 12)
        );

        PurchaseResult result = service.purchase(manualLottoNumbers, 1);

        assertEquals(2, result.manualCount());
        assertEquals(1, result.autoCount());
        assertEquals(3, result.purchasedNumbers().size());
        assertEquals(List.of(1, 2, 3, 4, 5, 6), result.purchasedNumbers().get(0).getNumbers());
        assertEquals(List.of(7, 8, 9, 10, 11, 12), result.purchasedNumbers().get(1).getNumbers());
    }

    @Test
    @DisplayName("수동 번호 목록이 null이면 예외가 발생한다")
    void test_purchase_null_manual_lotto_numbers() {
        LottoPurchaseService service = new LottoPurchaseService(new LottoNumberGenerator(1L));

        assertThrows(IllegalArgumentException.class, () -> service.purchase(null, 1));
    }
}
