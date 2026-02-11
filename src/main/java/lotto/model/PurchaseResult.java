package lotto.model;

import java.util.List;

public record PurchaseResult(
    List<PurchasedLottoNumbers> purchasedNumbers,
    int manualCount,
    int autoCount
) {
    public PurchaseResult {
        validateCounts(manualCount, autoCount);
        validatePurchasedNumbersSize(purchasedNumbers, manualCount, autoCount);
        purchasedNumbers = List.copyOf(purchasedNumbers);
    }

    private void validateCounts(int manualCount, int autoCount) {
        if (manualCount < 0 || autoCount < 0) {
            throw new IllegalArgumentException("구매 개수는 0 이상이어야 합니다.");
        }
    }

    private void validatePurchasedNumbersSize(List<PurchasedLottoNumbers> purchasedNumbers, int manualCount, int autoCount) {
        if (purchasedNumbers == null) {
            throw new IllegalArgumentException("구매한 로또 목록은 비어 있을 수 없습니다.");
        }
        if (purchasedNumbers.size() != manualCount + autoCount) {
            throw new IllegalArgumentException("구매한 로또 개수와 수동/자동 개수가 일치해야 합니다.");
        }
    }
}
