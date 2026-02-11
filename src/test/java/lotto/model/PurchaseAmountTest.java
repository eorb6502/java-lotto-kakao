package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PurchaseAmountTest {

    @Test
    @DisplayName("구입금액은 1000원 이상이면 허용된다")
    void test_valid_purchase_amount() {
        assertDoesNotThrow(() -> new PurchaseAmount(1000));
        assertDoesNotThrow(() -> new PurchaseAmount(1500));
    }

    @Test
    @DisplayName("구입금액은 1000원 미만이면 예외")
    void test_invalid_purchase_amount() {
        assertThrows(IllegalArgumentException.class, () -> new PurchaseAmount(999));
    }

    @Test
    @DisplayName("구매 개수 계산은 1000원으로 나눈 몫을 사용한다")
    void test_lotto_count_uses_quotient() {
        PurchaseAmount amount = new PurchaseAmount(1500);
        assertEquals(1, amount.toLottoCount());
    }
}
