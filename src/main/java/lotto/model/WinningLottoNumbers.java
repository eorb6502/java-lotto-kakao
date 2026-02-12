package lotto.model;

import java.util.List;

public class WinningLottoNumbers {
    private final LottoNumbers numbers;
    private final LottoNumber bonusNumber;

    public WinningLottoNumbers(List<Integer> numbers, int bonusNumber) {
        this(new LottoNumbers(numbers), new LottoNumber(bonusNumber));
    }

    public WinningLottoNumbers(LottoNumbers numbers, int bonusNumber) {
        this(numbers, new LottoNumber(bonusNumber));
    }

    private WinningLottoNumbers(LottoNumbers numbers, LottoNumber bonusNumber) {
        validateNumbers(numbers);
        validateBonusNotDuplicated(numbers, bonusNumber);
        this.numbers = numbers;
        this.bonusNumber = bonusNumber;
    }

    public LottoResult compare(PurchasedLottoNumbers purchasedNumbers) {
        int matchCount = numbers.countMatches(purchasedNumbers.numbers());
        boolean isBonusMatched = purchasedNumbers.numbers().contains(bonusNumber);
        return LottoResult.from(matchCount, isBonusMatched);
    }

    public List<Integer> getNumbers() {
        return numbers.getNumbers();
    }

    public int getBonus() {
        return bonusNumber.value();
    }

    private void validateNumbers(LottoNumbers numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("당첨 번호는 비어 있을 수 없습니다.");
        }
    }

    private void validateBonusNotDuplicated(LottoNumbers numbers, LottoNumber bonusNumber) {
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 중복될 수 없습니다.");
        }
    }
}
