package lotto.controller;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoStatistics;
import lotto.model.ManualPurchaseCount;
import lotto.model.PurchaseAmount;
import lotto.model.PurchaseResult;
import lotto.model.WinningLottoNumbers;
import lotto.service.LottoPurchaseService;
import lotto.view.InputHistoryView;
import lotto.view.InputPriceView;
import lotto.view.InputManualView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {
    private final InputPriceView inputPriceView;
    private final InputManualView inputManualView;
    private final InputHistoryView inputHistoryView;
    private final OutputView outputView;

    private final LottoPurchaseService lottoPurchaseService;

    public LottoController() {
        inputPriceView = new InputPriceView();
        inputManualView = new InputManualView();
        inputHistoryView = new InputHistoryView();
        outputView = new OutputView();
        LottoNumberGenerator lottoGenerator = new LottoNumberGenerator();
        lottoPurchaseService = new LottoPurchaseService(lottoGenerator);
    }

    public void run() {
        // 구입 금액 입력
        PurchaseAmount purchaseAmount = doInputPrice();
        int totalCount = purchaseAmount.toLottoCount();

        // 수동 개수 입력 & 자동 개수 계산
        ManualPurchaseCount manualCount = doInputManualCount(totalCount);
        int autoCount = totalCount - manualCount.value();

        // 로또 구매 (수동은 번호 입력 & 자동은 번호 생성)
        final PurchaseResult purchaseResult = doPurchase(manualCount.value(), autoCount);
        outputView.printPurchasedLottos(purchaseResult);

        // 지난주 결과 입력
        final WinningLottoNumbers winningNumber = doInputWinningNumbers();

        // 통계 계산 및 출력
        LottoStatistics statistics = LottoStatistics.from(
            winningNumber,
            purchaseResult.purchasedNumbers(),
            purchaseAmount
        );
        outputView.printStatistics(statistics.resultCountByRank(), statistics.profitRate());
    }

    private PurchaseAmount doInputPrice() {
        return new PurchaseAmount(inputPriceView.inputPrice());
    }

    private ManualPurchaseCount doInputManualCount(int maxCount) {
        return ManualPurchaseCount.of(inputManualView.inputManualCount(maxCount), maxCount);
    }

    private PurchaseResult doPurchase(int manualCount, int autoCount) {
        List<List<Integer>> manualLottoNumbers = inputManualView.inputManualLottos(manualCount);
        try {
            return lottoPurchaseService.purchase(manualLottoNumbers, autoCount);
        } catch (IllegalArgumentException exception) {
            outputView.printString(exception.getMessage());
        }
        return doPurchase(manualCount, autoCount);
    }

    private WinningLottoNumbers doInputWinningNumbers() {
        List<Integer> numbers = inputHistoryView.inputWinningNumbers();
        int bonusNumber = inputHistoryView.inputBonusNumber();
        try {
            return new WinningLottoNumbers(numbers, bonusNumber);
        } catch (IllegalArgumentException exception) {
            outputView.printString(exception.getMessage());
        }
        return doInputWinningNumbers();
    }
}
