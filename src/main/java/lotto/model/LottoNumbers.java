package lotto.model;

import java.util.*;

public class LottoNumbers {
    private static final int LOTTO_SIZE = 6;

    private final List<Integer> numbers;
    private final Integer bonusNumber;

    public LottoNumbers(List<Integer> numbers) {
        this(numbers, 0, false);
    }

    public LottoNumbers(List<Integer> numbers, Integer bonusNumber) {
        this(numbers, bonusNumber, true);
    }

    private LottoNumbers(List<Integer> numbers, Integer bonusNumber, boolean checkBonus) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("숫자는 6개이어야 합니다.");
        }
        for (Integer number : numbers) {
            if (number < 1 || number > 45) {
                throw new IllegalArgumentException("숫자의 범위는 1 이상 45 이하이어야 합니다.");
            }
        }
        if (checkBonus && (bonusNumber < 1 || bonusNumber > 45)) {
            throw new IllegalArgumentException("보너스 숫자의 범위는 1 이상 45 이하이어야 합니다.");
        }
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("숫자는 중복될 수 없습니다.");
        }
        this.numbers = numbers;
        this.bonusNumber = bonusNumber;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
    public Integer getBonus() {
        return bonusNumber;
    }

    public LottoResult compare(LottoNumbers other) {
        if ((this.bonusNumber == 0 && other.bonusNumber == 0) ||
            (this.bonusNumber != 0 && other.bonusNumber != 0)) {
            throw new IllegalArgumentException("두 로또 번호 조합 중 반드시 한 개의 로또 번호에만 보너스 번호가 있어야 합니다.");
        }
        if (this.bonusNumber == 0) {
            return other.compare(this);
        }
        List<Integer> otherNumbers = other.getNumbers();
        List<Integer> intersection = new ArrayList<>(this.numbers);
        intersection.retainAll(otherNumbers);

        if (intersection.size() == 6) {
            return LottoResult.RANK_FIRST;
        }
        if (intersection.size() == 5) {
            if (otherNumbers.contains(this.bonusNumber)) {
                return LottoResult.RANK_SECOND;
            }
            return LottoResult.RANK_THIRD;
        }
        if (intersection.size() == 4) {
            return LottoResult.RANK_FOURTH;
        }
        if (intersection.size() == 3) {
            return LottoResult.RANK_FIFTH;
        }
        return LottoResult.RANK_NONE;
        // this와 other의 교집합이 6개 -> 1등
        // 5개 -> 2등 or 3등
            // 보너스와 나머지 한개 비교
            // 보너스가 0이 아닌 객체의 보너스와, 0인 객체의 남는 숫자 비교
        // 4개 -> 4등
        // 3개 -> 5등
    }

    public String toString() {
        return numbers.toString();
    }
}
