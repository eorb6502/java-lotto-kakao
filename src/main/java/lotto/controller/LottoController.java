package lotto.controller;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoNumbers;
import lotto.model.LottoStatistics;
import lotto.model.PurchaseResult;
import lotto.view.InputHistoryView;
import lotto.view.InputPriceView;
import lotto.view.InputManualView;
import lotto.view.OutputView;

import java.util.List;

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
        int price = doInputPrice();
        int totalCount = price / LOTTO_PRICE;

        // 수동 개수 입력 & 자동 개수 계산
        int manualCount = doInputManualCount(totalCount);
        int autoCount = totalCount - manualCount;

        // 로또 구매 (수동은 번호 입력 & 자동은 번호 생성)
        final PurchaseResult purchaseResult = doPurchase(manualCount, autoCount);
        outputView.printPurchasedLottos(purchaseResult);

        // 지난주 결과 입력
        final LottoNumbers winningNumber = doInputWinningNumbers();

        // 통계 계산 및 출력
        LottoStatistics statistics = LottoStatistics.from(winningNumber, purchaseResult.purchasedNumbers(), price);
        outputView.printStatistics(statistics.resultCountByRank(), statistics.profitRate());
    }

    private int doInputPrice() {
        return inputPriceView.inputPrice();
    }

    private int doInputManualCount(int maxCount) {
        return inputManualView.inputManualCount(maxCount);
    }

    private PurchaseResult doPurchase(int manualCount, int autoCount) {
        List<LottoNumbers> purchasedNumbers = inputManualView.inputManualLottos(manualCount);
        List<LottoNumbers> autoNumbers = lottoGenerator.generate(autoCount);
        purchasedNumbers.addAll(autoNumbers);
        return new PurchaseResult(purchasedNumbers, manualCount, autoCount);
    }

    private LottoNumbers doInputWinningNumbers() {
        List<Integer> numbers = inputHistoryView.inputWinningNumbers();
        int bonusNumber = inputHistoryView.inputBonusNumber(numbers);
        return new LottoNumbers(numbers, bonusNumber);
    }
}
