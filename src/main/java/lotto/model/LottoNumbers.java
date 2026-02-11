package lotto.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LottoNumbers {
    private static final int LOTTO_SIZE = 6;

    private final List<LottoNumber> numbers;
    private final LottoNumber bonusNumber;

    public LottoNumbers(List<Integer> numbers) {
        this(convertNumbers(numbers), null);
    }

    public LottoNumbers(List<Integer> numbers, Integer bonusNumber) {
        this(convertNumbers(numbers), new LottoNumber(bonusNumber));
    }

    private LottoNumbers(List<LottoNumber> numbers, LottoNumber bonusNumber) {
        // 숫자 개수 검증
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("로또 번호는 6개의 숫자이어야 합니다.");
        }
        // 숫자 중복 검증
        final Set<Integer> set = new HashSet<>();
        for (final LottoNumber number : numbers) {
            set.add(number.value());
        }
        if (set.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("로또 번호는 중복될 수 없습니다.");
        }
        // 보너스 번호 중복 검증
        if (bonusNumber != null && set.contains(bonusNumber.value())) {
            throw new IllegalArgumentException("보너스 번호는 중복될 수 없습니다.");
        }
        this.numbers = sortNumbers(numbers);
        this.bonusNumber = bonusNumber;
    }

    public List<Integer> getNumbers() {
        List<Integer> list = new ArrayList<>();
        for (final LottoNumber number : numbers) {
            list.add(number.value());
        }
        return list;
    }

    public int getBonus() {
        if (bonusNumber == null) return 0;
        return bonusNumber.value();
    }

    public LottoResult compare(LottoNumbers other) {
        if ((this.bonusNumber == null && other.bonusNumber == null) ||
            (this.bonusNumber != null && other.bonusNumber != null)) {
            throw new IllegalArgumentException("두 로또 번호 중 한 개의 로또 번호에만 보너스 번호가 있어야 합니다.");
        }
        if (this.bonusNumber == null) {
            return other.compare(this);
        }
        // 두 로또 번호 비교
        final List<Integer> otherNumbers = other.getNumbers();
        int matchCount = calculateIntersectionSize(otherNumbers);
        boolean isBonusMatched = otherNumbers.contains(bonusNumber.value());
        return determineResult(matchCount, isBonusMatched);
    }

    private int calculateIntersectionSize(List<Integer> otherNumbers) {
        return (int) numbers.stream()
            .map(LottoNumber::value)
            .filter(otherNumbers::contains)
            .count();
    }

    private LottoResult determineResult(int matchCount, boolean bonusMatched) {
        if (matchCount == 6) return LottoResult.RANK_FIRST;
        if (matchCount == 5 && bonusMatched) return LottoResult.RANK_SECOND;
        if (matchCount == 5) return LottoResult.RANK_THIRD;
        if (matchCount == 4) return LottoResult.RANK_FOURTH;
        if (matchCount == 3) return LottoResult.RANK_FIFTH;
        return LottoResult.RANK_NONE;
    }

    private static List<LottoNumber> convertNumbers(List<Integer> numbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            lottoNumbers.add(new LottoNumber(number));
        }
        return lottoNumbers;
    }

    private List<LottoNumber> sortNumbers(List<LottoNumber> numbers) {
        List<LottoNumber> sortedNumbers = new ArrayList<>(numbers);
        sortedNumbers.sort(Comparator.comparingInt(LottoNumber::value));
        return List.copyOf(sortedNumbers);
    }

    public String toString() {
        return getNumbers().toString();
    }
}
