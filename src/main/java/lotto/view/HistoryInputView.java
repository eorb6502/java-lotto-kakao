package lotto.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoryInputView {
    private static final int LOTTO_NUMBER_COUNT = 6;

    private final Scanner scanner;

    public HistoryInputView() {
        this.scanner = new Scanner(System.in);
    }

    public List<Integer> inputWinningNumbers() {
        System.out.println("지난 주 당첨 번호를 입력해 주세요.");
        String[] winningNumbers = scanner.nextLine().split(",");
        return parseWinningNumbers(winningNumbers);
    }

    public int inputBonusNumber() {
        System.out.println("보너스 볼을 입력해 주세요.");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private List<Integer> parseWinningNumbers(String[] winningNumbers) {
        if (winningNumbers.length != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException("당첨 번호는 6개를 입력해야 합니다.");
        }
        List<Integer> parsedWinningNumbers = new ArrayList<>();
        for (String winningNumber : winningNumbers) {
            parsedWinningNumbers.add(Integer.parseInt(winningNumber.trim()));
        }
        return parsedWinningNumbers;
    }
}
