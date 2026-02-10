package lotto.view;

import lotto.model.LottoNumbers;
import lotto.model.LottoResult;
import lotto.model.PurchaseResult;

import java.util.List;
import java.util.Map;

public class OutputView {

    public void printPurchasedLottos(final PurchaseResult result) {
        System.out.println("수동으로 " + result.manualCount() + "장, 자동으로 " + result.autoCount() + "개를 구매했습니다.");
        for (final LottoNumbers purchasedLotto : result.purchasedNumbers()) {
            System.out.println(purchasedLotto.getNumbers());
        }
        System.out.println();
    }

    public void printStatistics(Map<LottoResult, Integer> resultCountByRank, double profitRate) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---------");
        System.out.printf("3개 일치 (%s)- %d개%n",
            formatPrize(LottoResult.RANK_FIFTH.getPrize()),
            resultCountByRank.getOrDefault(LottoResult.RANK_FIFTH, 0));
        System.out.printf("4개 일치 (%s)- %d개%n",
            formatPrize(LottoResult.RANK_FOURTH.getPrize()),
            resultCountByRank.getOrDefault(LottoResult.RANK_FOURTH, 0));
        System.out.printf("5개 일치 (%s)- %d개%n",
            formatPrize(LottoResult.RANK_THIRD.getPrize()),
            resultCountByRank.getOrDefault(LottoResult.RANK_THIRD, 0));
        System.out.printf("5개 일치, 보너스 볼 일치(%s) - %d개%n",
            formatPrize(LottoResult.RANK_SECOND.getPrize()),
            resultCountByRank.getOrDefault(LottoResult.RANK_SECOND, 0));
        System.out.printf("6개 일치 (%s)- %d개%n",
            formatPrize(LottoResult.RANK_FIRST.getPrize()),
            resultCountByRank.getOrDefault(LottoResult.RANK_FIRST, 0));
        System.out.printf("총 수익률은 %.2f입니다.%n", profitRate);
    }

    private String formatPrize(long prize) {
        return String.format("%,d원", prize);
    }
}
