package lotto.controller;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoNumbers;
import lotto.model.LottoResult;
import lotto.model.PurchaseResult;
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
    private final InputManualView inputManualView;
    private final InputHistoryView inputHistoryView;
    private final OutputView outputView;

    private final LottoNumberGenerator lottoGenerator;

    public LottoController() {
        inputPriceView = new InputPriceView(LOTTO_PRICE);
        inputManualView = new InputManualView();
        inputHistoryView = new InputHistoryView();
        outputView = new OutputView();
        lottoGenerator = new LottoNumberGenerator();
    }

    public void run() {
        // 구입 금액 입력
        int price = inputPriceView.inputPrice();
        int totalCount = price / LOTTO_PRICE;

        PurchaseResult purchaseResult = purchaseLottos(totalCount);
        outputView.printPurchasedLottos(purchaseResult);

        LottoNumbers historyNumber = readWinningNumbers(inputHistoryView);
        Map<LottoResult, Integer> resultCountByRank = countByRank(historyNumber, purchaseResult.purchasedNumbers());
        outputView.printStatistics(resultCountByRank, calculateProfitRate(price, resultCountByRank));
    }

    private PurchaseResult purchaseLottos(int totalCount) {
        int manualCount = inputManualView.inputManualCount(totalCount);
        int autoCount = totalCount - manualCount;

        List<LottoNumbers> purchasedNumbers = inputManualView.inputManualLottos(manualCount);
        List<LottoNumbers> autoNumbers = lottoGenerator.generate(autoCount);
        purchasedNumbers.addAll(autoNumbers);
        return new PurchaseResult(purchasedNumbers, manualCount, autoCount);
    }

    private LottoNumbers readWinningNumbers(InputHistoryView historyInputView) {
        List<Integer> num = historyInputView.inputWinningNumbers();
        int bonusNum = historyInputView.inputBonusNumber(num);
        return new LottoNumbers(num, bonusNum);
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
        return (double)calculateTotalPrize(resultCountByRank) / price;
    }

    private long calculateTotalPrize(Map<LottoResult, Integer> resultCountByRank) {
        long totalPrize = 0L;
        for (Map.Entry<LottoResult, Integer> entry : resultCountByRank.entrySet()) {
            totalPrize += entry.getKey().getPrize() * entry.getValue();
        }
        return totalPrize;
    }
}
