package lotto.model;

import java.util.List;

public record PurchaseResult(
    List<LottoNumbers> purchasedNumbers,
    int manualCount,
    int autoCount) {
}
