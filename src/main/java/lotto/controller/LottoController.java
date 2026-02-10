package lotto.controller;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoNumbers;
import lotto.model.LottoResult;
import lotto.view.InputHistoryView;
import lotto.view.InputPriceView;
import lotto.view.InputManualView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoController {
    private static final int LOTTO_PRICE = 1000;

    private final InputPriceView inputPriceView;
    private final InputManualView manualLottoInputView = new InputManualView();
    private final InputHistoryView inputHistoryView;
    private final OutputView outputView = new OutputView();

    public LottoController() {
        inputPriceView = new InputPriceView(LOTTO_PRICE);
        inputHistoryView = new InputHistoryView();
    }

    public void run() {
        int price = inputPriceView.inputPrice();
        int totalCount = price / LOTTO_PRICE;

        PurchaseResult purchaseResult = purchaseLottos(manualLottoInputView, totalCount);
        outputView.printPurchasedLottos(purchaseResult.purchasedNumbers, purchaseResult.manualCount, purchaseResult.autoCount);

        LottoNumbers historyNumber = readWinningNumbers(inputHistoryView);
        Map<LottoResult, Integer> resultCountByRank = countByRank(historyNumber, purchaseResult.purchasedNumbers);
        outputView.printStatistics(resultCountByRank, calculateProfitRate(price, resultCountByRank));
    }

    private PurchaseResult purchaseLottos(InputManualView manualLottoInputView, int totalCount) {
        int manualCount = manualLottoInputView.inputManualCount(totalCount);
        List<LottoNumbers> manualNumbers = manualLottoInputView.inputManualLottos(manualCount);
        int autoCount = totalCount - manualCount;
        List<LottoNumbers> autoNumbers = generateAutoLottos(autoCount);
        List<LottoNumbers> purchasedNumbers = mergeManualAndAuto(manualNumbers, autoNumbers);
        return new PurchaseResult(purchasedNumbers, manualCount, autoCount);
    }

    private List<LottoNumbers> generateAutoLottos(int autoCount) {
        LottoNumberGenerator generator = new LottoNumberGenerator();
        return generator.generate(autoCount);
    }

    private LottoNumbers readWinningNumbers(InputHistoryView historyInputView) {
        List<Integer> num = historyInputView.inputWinningNumbers();
        int bonusNum = historyInputView.inputBonusNumber(num);
        return new LottoNumbers(num, bonusNum);
    }

    private List<LottoNumbers> mergeManualAndAuto(List<LottoNumbers> manualNumbers,
        List<LottoNumbers> autoNumbers) {
        List<LottoNumbers> mergedNumbers = new ArrayList<>();
        mergedNumbers.addAll(manualNumbers);
        mergedNumbers.addAll(autoNumbers);
        return mergedNumbers;
    }

    private Map<LottoResult, Integer> countByRank(LottoNumbers historyNumber, List<LottoNumbers> generatedNumbers) {
        Map<LottoResult, Integer> resultCountByRank = new HashMap<>();
        for (LottoNumbers numbers : generatedNumbers) {
            LottoResult result = historyNumber.compare(numbers);
            resultCountByRank.merge(result, 1, Integer::sum);
        }
        return resultCountByRank;
    }

    private double calculateProfitRate(int price, Map<LottoResult, Integer> resultCountByRank) {
        long totalPrize = calculateTotalPrize(resultCountByRank);
        return (double) totalPrize / price;
    }

    private long calculateTotalPrize(Map<LottoResult, Integer> resultCountByRank) {
        long totalPrize = 0L;
        for (Map.Entry<LottoResult, Integer> entry : resultCountByRank.entrySet()) {
            totalPrize += entry.getKey().getPrize() * entry.getValue();
        }
        return totalPrize;
    }

    private class PurchaseResult {
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
