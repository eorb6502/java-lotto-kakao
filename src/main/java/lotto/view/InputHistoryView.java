package lotto.view;

import lotto.model.LottoNumber;
import lotto.model.LottoNumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHistoryView {
    private static final String LOTTO_NUMBER_DELIMITER_REGEX = "\\s*,\\s*";
    private static final String INVALID_BONUS_MESSAGE = "보너스 볼은 공백 없이 숫자만 입력해야 합니다.";
    private static final String INVALID_WINNING_MESSAGE = "당첨 번호는 쉼표로 구분된 숫자만 입력해야 합니다.";

    private final Scanner scanner;

    public InputHistoryView() {
        this(new Scanner(System.in));
    }

    public InputHistoryView(Scanner scanner) {
        validateScanner(scanner);
        this.scanner = scanner;
    }

    public LottoNumbers inputWinningNumbers() {
        while (true) {
            try {
                System.out.println("지난 주 당첨 번호를 입력해 주세요.");
                String[] winningNumbers = scanner.nextLine().split(LOTTO_NUMBER_DELIMITER_REGEX);
                return new LottoNumbers(parseWinningNumbers(winningNumbers));
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public int inputBonusNumber() {
        while (true) {
            try {
                System.out.println("보너스 볼을 입력해 주세요.");
                return parseBonusNumber(scanner.nextLine());
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void validateScanner(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("입력 스캐너는 비어 있을 수 없습니다.");
        }
    }

    private List<Integer> parseWinningNumbers(String[] winningNumbers) {
        List<Integer> parsedWinningNumbers = new ArrayList<>();
        for (String winningNumber : winningNumbers) {
            parsedWinningNumbers.add(parseWinningNumber(winningNumber));
        }
        return parsedWinningNumbers;
    }

    private int parseWinningNumber(String winningNumber) {
        try {
            int value = Integer.parseInt(winningNumber.trim());
            return new LottoNumber(value).value();
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(INVALID_WINNING_MESSAGE, exception);
        }
    }

    private int parseBonusNumber(String bonusInput) {
        try {
            int value = Integer.parseInt(bonusInput.trim());
            return new LottoNumber(value).value();
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(INVALID_BONUS_MESSAGE, exception);
        }
    }
}
