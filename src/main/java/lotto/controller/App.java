package lotto.controller;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoNumbers;
import lotto.view.HistoryInputView;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class App {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        HistoryInputView historyInputView = new HistoryInputView();
        OutputView outputView = new OutputView();

        int price = inputView.inputPrice();
        LottoNumberGenerator generator = new LottoNumberGenerator();
        List<LottoNumbers> numbers = generator.generate(price / 1000);
        for (LottoNumbers number : numbers) {
            System.out.println(number.toString());
        }

    }
}
