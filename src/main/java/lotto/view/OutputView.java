package lotto.view;

import lotto.model.LottoNumbers;
import lotto.model.LottoResult;

import java.util.List;
import java.util.Map;

public class OutputView {
    public void printPurchasedLottos(List<LottoNumbers> purchasedLottos) {
        System.out.println(purchasedLottos.size() + "개를 구매했습니다.");
        for (LottoNumbers purchasedLotto : purchasedLottos) {
            System.out.println(purchasedLotto.getNumbers());
        }
    }

    public void printStatistics(Map<LottoResult, Integer> resultCountByRank, double profitRate) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---------");
        System.out.printf("3개 일치 (5000원)- %d개%n", resultCountByRank.getOrDefault(LottoResult.RANK_FIFTH, 0));
        System.out.printf("4개 일치 (50000원)- %d개%n", resultCountByRank.getOrDefault(LottoResult.RANK_FOURTH, 0));
        System.out.printf("5개 일치 (1500000원)- %d개%n", resultCountByRank.getOrDefault(LottoResult.RANK_THIRD, 0));
        System.out.printf("5개 일치, 보너스 볼 일치(30000000원) - %d개%n", resultCountByRank.getOrDefault(LottoResult.RANK_SECOND, 0));
        System.out.printf("6개 일치 (2000000000원)- %d개%n", resultCountByRank.getOrDefault(LottoResult.RANK_FIRST, 0));
        System.out.printf("총 수익률은 %.2f입니다.%n", profitRate);
    }
}
