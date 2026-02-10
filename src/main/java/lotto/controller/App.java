package lotto.controller;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoNumbers;
import lotto.model.LottoResult;
import lotto.view.HistoryInputView;
import lotto.view.InputView;
import lotto.view.ManualLottoInputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private static final int LOTTO_PRICE = 1000;

    private static final InputView inputView = new InputView();
    private static final ManualLottoInputView manualLottoInputView = new ManualLottoInputView();
    private static final HistoryInputView historyInputView = new HistoryInputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        int price = inputView.inputPrice();
        int totalCount = price / LOTTO_PRICE;
        PurchaseResult purchaseResult = purchaseLottos(manualLottoInputView, totalCount);
        outputView.printPurchasedLottos(purchaseResult.purchasedNumbers, purchaseResult.manualCount, purchaseResult.autoCount);

        LottoNumbers historyNumber = readWinningNumbers(historyInputView);
        Map<LottoResult, Integer> resultCountByRank = countByRank(historyNumber, purchaseResult.purchasedNumbers);
        outputView.printStatistics(resultCountByRank, calculateProfitRate(price, resultCountByRank));
    }

    private static PurchaseResult purchaseLottos(ManualLottoInputView manualLottoInputView, int totalCount) {
        int manualCount = manualLottoInputView.inputManualCount(totalCount);
        List<LottoNumbers> manualNumbers = manualLottoInputView.inputManualLottos(manualCount);
        int autoCount = totalCount - manualCount;
        List<LottoNumbers> autoNumbers = generateAutoLottos(autoCount);
        List<LottoNumbers> purchasedNumbers = mergeManualAndAuto(manualNumbers, autoNumbers);
        return new PurchaseResult(purchasedNumbers, manualCount, autoCount);
    }

    private static List<LottoNumbers> generateAutoLottos(int autoCount) {
        LottoNumberGenerator generator = new LottoNumberGenerator();
        return generator.generate(autoCount);
    }

    private static LottoNumbers readWinningNumbers(HistoryInputView historyInputView) {
        List<Integer> num = historyInputView.inputWinningNumbers();
        int bonusNum = historyInputView.inputBonusNumber();
        return new LottoNumbers(num, bonusNum);
    }

    private static List<LottoNumbers> mergeManualAndAuto(List<LottoNumbers> manualNumbers,
        List<LottoNumbers> autoNumbers) {
        List<LottoNumbers> mergedNumbers = new ArrayList<>();
        mergedNumbers.addAll(manualNumbers);
        mergedNumbers.addAll(autoNumbers);
        return mergedNumbers;
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
            totalPrize += entry.getKey().getPrize() * entry.getValue();
        }
        return totalPrize;
    }

    private static class PurchaseResult {
        private final List<LottoNumbers> purchasedNumbers;
        private final int manualCount;
        private final int autoCount;

        private PurchaseResult(List<LottoNumbers> purchasedNumbers, int manualCount, int autoCount) {
            this.purchasedNumbers = purchasedNumbers;
            this.manualCount = manualCount;
            this.autoCount = autoCount;
        }
    }
}
