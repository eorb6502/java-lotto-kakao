package lotto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record LottoStatistics(
    Map<LottoResult, Integer> resultCountByRank,
    double profitRate
) {
    public LottoStatistics {
        resultCountByRank = Map.copyOf(resultCountByRank);
    }

    public static LottoStatistics from(WinningLottoNumbers winningNumber, List<PurchasedLottoNumbers> purchasedNumbers, int purchasePrice) {
        PurchaseAmount amount = new PurchaseAmount(purchasePrice);
        Map<LottoResult, Integer> resultCountByRank = countByRank(winningNumber, purchasedNumbers);
        long totalPrize = calculateTotalPrize(resultCountByRank);
        double profitRate = (double) totalPrize / amount.value();
        return new LottoStatistics(resultCountByRank, profitRate);
    }

    private static Map<LottoResult, Integer> countByRank(WinningLottoNumbers winningNumber, List<PurchasedLottoNumbers> purchasedNumbers) {
        Map<LottoResult, Integer> resultCountByRank = new HashMap<>();
        for (PurchasedLottoNumbers numbers : purchasedNumbers) {
            LottoResult result = winningNumber.compare(numbers);
            resultCountByRank.merge(result, 1, Integer::sum);
        }
        return resultCountByRank;
    }

    private static long calculateTotalPrize(Map<LottoResult, Integer> resultCountByRank) {
        long totalPrize = 0L;
        for (Map.Entry<LottoResult, Integer> entry : resultCountByRank.entrySet()) {
            totalPrize += entry.getKey().getPrize() * entry.getValue();
        }
        return totalPrize;
    }
}
