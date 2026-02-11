package lotto.model;

public record ManualPurchaseCount(int value) {

    public static ManualPurchaseCount of(int manualCount, int maxCount) {
        validateRange(manualCount, maxCount);
        return new ManualPurchaseCount(manualCount);
    }

    private static void validateRange(int manualCount, int maxCount) {
        if (maxCount < 0 || manualCount < 0 || manualCount > maxCount) {
            throw new IllegalArgumentException("수동 구매 개수는 0 이상 구매 가능 개수 이하여야 합니다.");
        }
    }
}
