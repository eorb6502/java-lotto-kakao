package lotto.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LottoNumbers {
    private static final int LOTTO_SIZE = 6;

    private final List<LottoNumber> numbers;
    private final LottoNumber bonusNumber;

    public LottoNumbers(List<Integer> numbers) {
        this(convertNumbers(numbers), null, false);
    }

    public LottoNumbers(List<Integer> numbers, Integer bonusNumber) {
        this(convertNumbers(numbers), new LottoNumber(bonusNumber), true);
    }

    private LottoNumbers(List<LottoNumber> numbers, LottoNumber bonusNumber, boolean checkBonus) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("숫자는 6개이어야 합니다.");
        }
        if (checkBonus && bonusNumber == null) {
            throw new IllegalArgumentException("보너스 숫자의 범위는 1 이상 45 이하이어야 합니다.");
        }
        validateNoDuplicates(numbers);
        this.numbers = numbers;
        this.bonusNumber = bonusNumber;
    }

    public List<Integer> getNumbers() {
        return toIntegerList(numbers);
    }

    public Integer getBonus() {
        if (bonusNumber == null) {
            return 0;
        }
        return bonusNumber.getValue();
    }

    public LottoResult compare(LottoNumbers other) {
        if ((this.bonusNumber == null && other.bonusNumber == null) ||
            (this.bonusNumber != null && other.bonusNumber != null)) {
            throw new IllegalArgumentException("두 로또 번호 조합 중 반드시 한 개의 로또 번호에만 보너스 번호가 있어야 합니다.");
        }
        if (this.bonusNumber == null) {
            return other.compare(this);
        }
        List<Integer> otherNumbers = other.getNumbers();
        int intersectionSize = calculateIntersectionSize(otherNumbers);

        if (intersectionSize == 6) {
            return LottoResult.RANK_FIRST;
        }
        if (intersectionSize == 5 && otherNumbers.contains(this.bonusNumber.getValue())) {
            return LottoResult.RANK_SECOND;
        }
        if (intersectionSize == 5) {
            return LottoResult.RANK_THIRD;
        }
        if (intersectionSize == 4) {
            return LottoResult.RANK_FOURTH;
        }
        if (intersectionSize == 3) {
            return LottoResult.RANK_FIFTH;
        }
        return LottoResult.RANK_NONE;
    }

    public String toString() {
        return getNumbers().toString();
    }

    private int calculateIntersectionSize(List<Integer> otherNumbers) {
        int count = 0;
        for (LottoNumber number : numbers) {
            if (otherNumbers.contains(number.getValue())) {
                count++;
            }
        }
        return count;
    }

    private static void validateNoDuplicates(List<LottoNumber> numbers) {
        Set<Integer> set = new HashSet<>();
        for (LottoNumber number : numbers) {
            set.add(number.getValue());
        }
        if (set.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("숫자는 중복될 수 없습니다.");
        }
    }

    private static List<LottoNumber> convertNumbers(List<Integer> numbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            lottoNumbers.add(new LottoNumber(number));
        }
        return lottoNumbers;
    }

    private static List<Integer> toIntegerList(List<LottoNumber> numbers) {
        List<Integer> values = new ArrayList<>();
        for (LottoNumber number : numbers) {
            values.add(number.getValue());
        }
        return values;
    }
}
