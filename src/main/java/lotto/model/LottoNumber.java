package lotto.model;

public record LottoNumber(int value) {
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 45;

    public LottoNumber {
        validateRange(value);
    }

    private void validateRange(int value) {
        if (value < MIN_NUMBER || value > MAX_NUMBER) {
            throw new IllegalArgumentException("숫자의 범위는 1 이상 45 이하이어야 합니다.");
        }
    }
}
