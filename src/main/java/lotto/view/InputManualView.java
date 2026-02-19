package lotto.view;

import lotto.model.ManualPurchaseCount;
import lotto.model.LottoNumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputManualView {
    private static final String DIGITS_ONLY_REGEX = "\\d+";
    private static final String LOTTO_INPUT_REGEX = "^\\d+(?:\\s*,\\s*\\d+)*$";
    private static final String LOTTO_NUMBER_DELIMITER_REGEX = "\\s*,\\s*";
    private static final int LOTTO_NUMBER_COUNT = 6;

    private final Scanner scanner;

    public InputManualView() {
        this(new Scanner(System.in));
    }

    public InputManualView(Scanner scanner) {
        validateScanner(scanner);
        this.scanner = scanner;
    }

    public int inputManualCount(int maxCount) {
        while (true) {
            System.out.println("수동으로 구매할 로또 수를 입력해 주세요.");
            try {
                String countInput = scanner.nextLine();
                int manualCount = validateAndParseCount(countInput);
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
            manualLottos.add(inputSingleLotto());
        }
        return manualLottos;
    }

    private LottoNumbers inputSingleLotto() {
        while (true) {
            try {
                String lottoInput = scanner.nextLine();
                validateLottoInputFormat(lottoInput);
                return parseLottoNumbers(lottoInput);
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

    private int validateAndParseCount(String input) {
        if (!input.matches(DIGITS_ONLY_REGEX)) {
            throw new IllegalArgumentException("수동 구매 개수는 공백 없이 숫자만 입력해야 합니다.");
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("수동 구매 개수는 공백 없이 숫자만 입력해야 합니다.");
        }
    }

    private void validateManualCountRange(int manualCount, int maxCount) {
        ManualPurchaseCount.of(manualCount, maxCount);
    }

    private void validateLottoInputFormat(String lottoInput) {
        if (!lottoInput.matches(LOTTO_INPUT_REGEX)) {
            throw new IllegalArgumentException("로또 번호는 쉼표로 구분된 숫자만 입력해야 합니다.");
        }
    }

    private LottoNumbers parseLottoNumbers(String lottoInput) {
        String[] tokens = lottoInput.split(LOTTO_NUMBER_DELIMITER_REGEX);
        if (tokens.length != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException("로또 번호는 6개를 입력해야 합니다.");
        }

        List<Integer> numbers = new ArrayList<>();
        for (String token : tokens) {
            numbers.add(Integer.parseInt(token.trim()));
        }
        return new LottoNumbers(numbers);
    }
}
