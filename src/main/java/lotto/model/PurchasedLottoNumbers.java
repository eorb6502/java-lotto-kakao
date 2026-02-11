package lotto.model;

import java.util.List;

public record PurchasedLottoNumbers(LottoNumbers numbers) {

    public PurchasedLottoNumbers {
        if (numbers == null) {
            throw new IllegalArgumentException("구매 로또 번호는 비어 있을 수 없습니다.");
        }
    }

    public PurchasedLottoNumbers(List<Integer> numbers) {
        this(new LottoNumbers(numbers));
    }

    public List<Integer> getNumbers() {
        return numbers.getNumbers();
    }
}
