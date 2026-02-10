package lotto.view;

import lotto.model.LottoNumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputManualView {
    private static final String DIGITS_ONLY_REGEX = "\\d+";
    private static final String LOTTO_INPUT_REGEX = "^\\d+(?:\\s*,\\s*\\d+)*$";
    private static final int LOTTO_NUMBER_COUNT = 6;
    private static final String INVALID_MANUAL_COUNT_MESSAGE = "수동 구매 개수는 공백 없이 숫자만 입력해야 합니다.";
    private static final String INVALID_MANUAL_RANGE_MESSAGE = "수동 구매 개수는 0 이상 구매 가능 개수 이하여야 합니다.";
    private static final String INVALID_LOTTO_FORMAT_MESSAGE = "로또 번호는 쉼표로 구분된 숫자만 입력해야 합니다.";
    private static final String INVALID_LOTTO_COUNT_MESSAGE = "로또 번호는 6개를 입력해야 합니다.";

    private final Scanner scanner;

    public InputManualView() {
        this.scanner = new Scanner(System.in);
    }

    public int inputManualCount(int maxCount) {
        while (true) {
            try {
                System.out.println("수동으로 구매할 로또 수를 입력해 주세요.");
                String countInput = scanner.nextLine();
                validateDigitsOnly(countInput);
                int manualCount = parseManualCount(countInput);
                validateManualCountRange(manualCount, maxCount);
                return manualCount;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public List<LottoNumbers> inputManualLottos(int manualCount) {
        List<LottoNumbers> manualLottos = new ArrayList<>();
        if (manualCount == 0) {
            return manualLottos;
        }
        System.out.println("수동으로 구매할 번호를 입력해 주세요.");
        for (int index = 0; index < manualCount; index++) {
            manualLottos.add(inputSingleManualLotto());
        }
        return manualLottos;
    }

    private LottoNumbers inputSingleManualLotto() {
        while (true) {
            try {
                String lottoInput = scanner.nextLine();
                validateLottoInputFormat(lottoInput);
                List<Integer> numbers = parseLottoNumbers(lottoInput);
                return new LottoNumbers(numbers);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void validateDigitsOnly(String countInput) {
        if (!countInput.matches(DIGITS_ONLY_REGEX)) {
            throw new IllegalArgumentException(INVALID_MANUAL_COUNT_MESSAGE);
        }
    }

    private int parseManualCount(String countInput) {
        try {
            return Integer.parseInt(countInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_MANUAL_COUNT_MESSAGE, e);
        }
    }

    private void validateManualCountRange(int manualCount, int maxCount) {
        if (manualCount < 0 || manualCount > maxCount) {
            throw new IllegalArgumentException(INVALID_MANUAL_RANGE_MESSAGE);
        }
    }

    private void validateLottoInputFormat(String lottoInput) {
        if (!lottoInput.matches(LOTTO_INPUT_REGEX)) {
            throw new IllegalArgumentException(INVALID_LOTTO_FORMAT_MESSAGE);
        }
    }

    private List<Integer> parseLottoNumbers(String lottoInput) {
        String[] tokens = lottoInput.split(",");
        validateLottoNumberCount(tokens);
        return parseNumbers(tokens);
    }

    private void validateLottoNumberCount(String[] tokens) {
        if (tokens.length != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(INVALID_LOTTO_COUNT_MESSAGE);
        }
    }

    private List<Integer> parseNumbers(String[] tokens) {
        List<Integer> numbers = new ArrayList<>();
        for (String token : tokens) {
            numbers.add(Integer.parseInt(token.trim()));
        }
        return numbers;
    }
}
