package lotto.controller;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoNumbers;
import lotto.model.LottoResult;
import lotto.view.HistoryInputView;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private static final int LOTTO_PRICE = 1000;
    private static final Map<LottoResult, Long> PRIZE_BY_RESULT = Map.of(
        LottoResult.RANK_FIRST, 2_000_000_000L,
        LottoResult.RANK_SECOND, 30_000_000L,
        LottoResult.RANK_THIRD, 1_500_000L,
        LottoResult.RANK_FOURTH, 50_000L,
        LottoResult.RANK_FIFTH, 5_000L,
        LottoResult.RANK_NONE, 0L
    );

    public static void main(String[] args) {
        InputView inputView = new InputView();
        HistoryInputView historyInputView = new HistoryInputView();
        OutputView outputView = new OutputView();

        int price = inputView.inputPrice();
        LottoNumberGenerator generator = new LottoNumberGenerator();
        List<LottoNumbers> generatedNumbers = generator.generate(price / LOTTO_PRICE);
        outputView.printPurchasedLottos(generatedNumbers);

        List<Integer> num = historyInputView.inputWinningNumbers();
        int bonusNum = historyInputView.inputBonusNumber();
        LottoNumbers historyNumber = new LottoNumbers(num, bonusNum);

        Map<LottoResult, Integer> resultCountByRank = countByRank(historyNumber, generatedNumbers);
        double profitRate = calculateProfitRate(price, resultCountByRank);
        outputView.printStatistics(resultCountByRank, profitRate);
    }

    private static Map<LottoResult, Integer> countByRank(LottoNumbers historyNumber, List<LottoNumbers> generatedNumbers) {
        Map<LottoResult, Integer> resultCountByRank = new HashMap<>();
        for (LottoNumbers numbers : generatedNumbers) {
            LottoResult result = historyNumber.compare(numbers);
            resultCountByRank.merge(result, 1, Integer::sum);
        }
        return resultCountByRank;
    }

    private static double calculateProfitRate(int price, Map<LottoResult, Integer> resultCountByRank) {
        long totalPrize = calculateTotalPrize(resultCountByRank);
        return (double) totalPrize / price;
    }

    private static long calculateTotalPrize(Map<LottoResult, Integer> resultCountByRank) {
        long totalPrize = 0L;
        for (Map.Entry<LottoResult, Integer> entry : resultCountByRank.entrySet()) {
            long prize = PRIZE_BY_RESULT.getOrDefault(entry.getKey(), 0L);
            totalPrize += prize * entry.getValue();
        }
        return totalPrize;
    }
}
