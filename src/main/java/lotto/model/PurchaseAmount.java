package lotto.model;

public record PurchaseAmount(int value) {
    public static final int LOTTO_PRICE = 1000;

    public PurchaseAmount {
        validateMinimum(value);
    }

    public int toLottoCount() {
        return value / LOTTO_PRICE;
    }

    private void validateMinimum(int value) {
        if (value < LOTTO_PRICE) {
            throw new IllegalArgumentException("구입금액은 " + LOTTO_PRICE + "원 이상이어야 합니다.");
        }
    }
}
