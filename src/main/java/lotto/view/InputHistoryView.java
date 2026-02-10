package lotto.view;

import lotto.model.LottoNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHistoryView {
    private static final int LOTTO_NUMBER_COUNT = 6;
    private static final String INVALID_BONUS_MESSAGE = "보너스 볼은 공백 없이 숫자만 입력해야 합니다.";
    private static final String INVALID_WINNING_MESSAGE = "당첨 번호는 쉼표로 구분된 숫자만 입력해야 합니다.";
    private static final String INVALID_WINNING_DUPLICATE_MESSAGE = "당첨 번호는 중복될 수 없습니다.";
    private static final String INVALID_BONUS_DUPLICATE_MESSAGE = "보너스 번호는 당첨 번호와 중복될 수 없습니다.";

    private final Scanner scanner;

    public InputHistoryView() {
        this.scanner = new Scanner(System.in);
    }

    public List<Integer> inputWinningNumbers() {
        try {
            System.out.println("지난 주 당첨 번호를 입력해 주세요.");
            String[] winningNumbers = scanner.nextLine().split(",");
            List<Integer> parsedWinningNumbers = parseWinningNumbers(winningNumbers);
            validateWinningNumbersDistinct(parsedWinningNumbers);
            return parsedWinningNumbers;
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return inputWinningNumbers();
        }
    }

    public int inputBonusNumber(List<Integer> winningNumbers) {
        try {
            System.out.println("보너스 볼을 입력해 주세요.");
            int bonusNumber = parseBonusNumber(scanner.nextLine());
            validateBonusNumberDistinct(winningNumbers, bonusNumber);
            return bonusNumber;
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return inputBonusNumber(winningNumbers);
        }
    }

    private List<Integer> parseWinningNumbers(String[] winningNumbers) {
        if (winningNumbers.length != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException("당첨 번호는 6개를 입력해야 합니다.");
        }
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

    private void validateWinningNumbersDistinct(List<Integer> winningNumbers) {
        long distinctCount = winningNumbers.stream().distinct().count();
        if (distinctCount != winningNumbers.size()) {
            throw new IllegalArgumentException(INVALID_WINNING_DUPLICATE_MESSAGE);
        }
    }

    private void validateBonusNumberDistinct(List<Integer> winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(INVALID_BONUS_DUPLICATE_MESSAGE);
        }
    }
}
