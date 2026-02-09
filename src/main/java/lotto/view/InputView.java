package lotto.view;

import java.util.Scanner;

public class InputView {
    private static final String DIGITS_ONLY_REGEX = "\\d+";
    private static final String INVALID_PRICE_MESSAGE = "구입금액은 공백 없이 숫자만 입력해야 합니다.";

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public int inputPrice() {
        System.out.println("구입금액을 입력해 주세요.");
        String priceInput = scanner.nextLine();
        validateDigitsOnly(priceInput);
        return parsePrice(priceInput);
    }

    private void validateDigitsOnly(String priceInput) {
        if (!priceInput.matches(DIGITS_ONLY_REGEX)) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
    }

    private int parsePrice(String priceInput) {
        try {
            return Integer.parseInt(priceInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE, e);
        }
    }
}
