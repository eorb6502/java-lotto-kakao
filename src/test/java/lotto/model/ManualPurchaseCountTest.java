package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ManualPurchaseCountTest {

    @Test
    @DisplayName("수동 구매 개수는 0 이상 최대 구매 개수 이하면 허용된다")
    void test_valid_manual_purchase_count() {
        assertDoesNotThrow(() -> ManualPurchaseCount.of(0, 5));
        assertDoesNotThrow(() -> ManualPurchaseCount.of(5, 5));
    }

    @Test
    @DisplayName("수동 구매 개수는 음수일 수 없다")
    void test_invalid_manual_purchase_count_negative() {
        assertThrows(IllegalArgumentException.class, () -> ManualPurchaseCount.of(-1, 5));
    }

    @Test
    @DisplayName("수동 구매 개수는 최대 구매 개수를 넘을 수 없다")
    void test_invalid_manual_purchase_count_over_max() {
        assertThrows(IllegalArgumentException.class, () -> ManualPurchaseCount.of(6, 5));
    }
}
