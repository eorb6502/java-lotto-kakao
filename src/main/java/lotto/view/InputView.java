package lotto.view;

import java.util.Scanner;

public class InputView {
    private static final String DIGITS_ONLY_REGEX = "\\d+";
    private static final String INVALID_PRICE_MESSAGE = "구입금액은 공백 없이 숫자만 입력해야 합니다.";
    private static final int LOTTO_PRICE = 1000;
    private static final String INVALID_MIN_PRICE_MESSAGE = "구입금액은 1000원 이상이어야 합니다.";

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public int inputPrice() {
        while (true) {
            try {
                System.out.println("구입금액을 입력해 주세요.");
                String priceInput = scanner.nextLine();
                validateDigitsOnly(priceInput);
                int price = parsePrice(priceInput);
                validateMinimumPrice(price);
                return price;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
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

    private void validateMinimumPrice(int price) {
        if (price < LOTTO_PRICE) {
            throw new IllegalArgumentException(INVALID_MIN_PRICE_MESSAGE);
        }
    }
}
