package lotto.view;

import lotto.model.PurchaseAmount;

import java.util.Scanner;

public class InputPriceView {
    private static final String DIGITS_ONLY_REGEX = "\\d+";

    private final Scanner scanner;

    public InputPriceView() {
        this.scanner = new Scanner(System.in);
    }

    public int inputPrice() {
        System.out.println("구입금액을 입력해 주세요.");
        try {
            String priceInput = scanner.nextLine();
            validateDigitsOnly(priceInput);
            int price = parsePrice(priceInput);
            validatePurchaseAmount(price);
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

    private void validatePurchaseAmount(int price) {
        new PurchaseAmount(price);
    }
}
