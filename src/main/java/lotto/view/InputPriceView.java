package lotto.view;

import java.util.Scanner;

public class InputPriceView {
    private static final String DIGITS_ONLY_REGEX = "\\d+";

    private final int minPrice;
    private final Scanner scanner;

    public InputPriceView(int minPrice) {
        this.minPrice = minPrice;
        this.scanner = new Scanner(System.in);
    }

    public int inputPrice() {
        System.out.println("구입금액을 입력해 주세요.");
        try {
            String priceInput = scanner.nextLine();
            validateDigitsOnly(priceInput);
            int price = parsePrice(priceInput);
            validateMinimumPrice(price);
            return price;
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        return inputPrice();
    }

    private void validateDigitsOnly(String priceInput) {
        if (!priceInput.matches(DIGITS_ONLY_REGEX)) {
            throw new IllegalArgumentException("구입금액은 공백 없이 숫자만 입력해야 합니다.");
        }
    }

    private int parsePrice(String priceInput) {
        try {
            return Integer.parseInt(priceInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("구입금액은 공백 없이 숫자만 입력해야 합니다.");
        }
    }

    private void validateMinimumPrice(int price) {
        if (price < minPrice) {
            throw new IllegalArgumentException("구입금액은 " + minPrice + "원 이상이어야 합니다.");
        }
    }
}
