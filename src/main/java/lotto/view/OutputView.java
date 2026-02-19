package lotto.view;

import lotto.model.LottoResult;
import lotto.model.PurchasedLottoNumbers;
import lotto.model.PurchaseResult;

import java.util.List;
import java.util.Map;

public class OutputView {
    private static final List<LottoResult> STATISTICS_ORDER = List.of(
        LottoResult.RANK_FIFTH,
        LottoResult.RANK_FOURTH,
        LottoResult.RANK_THIRD,
        LottoResult.RANK_SECOND,
        LottoResult.RANK_FIRST
    );
    private static final Map<LottoResult, String> STATISTICS_FORMAT = Map.of(
        LottoResult.RANK_FIFTH, "3개 일치 (%s)- %d개%n",
        LottoResult.RANK_FOURTH, "4개 일치 (%s)- %d개%n",
        LottoResult.RANK_THIRD, "5개 일치 (%s)- %d개%n",
        LottoResult.RANK_SECOND, "5개 일치, 보너스 볼 일치(%s) - %d개%n",
        LottoResult.RANK_FIRST, "6개 일치 (%s)- %d개%n"
    );

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
        for (LottoResult lottoResult : STATISTICS_ORDER) {
            System.out.printf(
                STATISTICS_FORMAT.get(lottoResult),
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
