package lotto.view;

import lotto.model.LottoResult;
import lotto.model.PurchasedLottoNumbers;
import lotto.model.PurchaseResult;

import java.util.Map;

public class OutputView {
    public void printPurchasedLottos(final PurchaseResult result) {
        System.out.println("수동으로 " + result.manualCount() + "장, 자동으로 " + result.autoCount() + "개를 구매했습니다.");
        for (final PurchasedLottoNumbers purchasedLotto : result.purchasedNumbers()) {
            System.out.println(purchasedLotto.getNumbers());
        }
        System.out.println();
    }

    public void printStatistics(Map<LottoResult, Integer> resultCountByRank, double profitRate) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---------");
        for (LottoResult lottoResult : LottoResult.statisticsResults()) {
            System.out.printf(
                lottoResult.getStatisticsFormat(),
                formatPrize(lottoResult.getPrize()),
                resultCountByRank.getOrDefault(lottoResult, 0)
            );
        }
        System.out.printf("총 수익률은 %.2f입니다.%n", profitRate);
    }
    public void printString(String s) {
        System.out.println(s);
    }
    private String formatPrize(long prize) {
        return String.format("%,d원", prize);
    }
}
