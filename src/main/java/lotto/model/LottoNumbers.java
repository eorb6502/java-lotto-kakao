package lotto.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LottoNumbers {
    private static final int LOTTO_SIZE = 6;

    private final List<LottoNumber> numbers;

    public LottoNumbers(List<Integer> numbers) {
        List<LottoNumber> convertedNumbers = convertNumbers(numbers);
        validateSize(convertedNumbers);
        validateDistinct(convertedNumbers);
        this.numbers = sortNumbers(convertedNumbers);
    }

    public static int getLottoSize() {
        return LOTTO_SIZE;
    }

    private void validateSize(List<LottoNumber> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("로또 번호는 6개의 숫자이어야 합니다.");
        }
    }

    private void validateDistinct(List<LottoNumber> numbers) {
        final Set<LottoNumber> set = new HashSet<>(numbers);
        if (set.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("로또 번호는 중복될 수 없습니다.");
        }
    }

    public List<Integer> getNumbers() {
        List<Integer> list = new ArrayList<>();
        for (final LottoNumber number : numbers) {
            list.add(number.value());
        }
        return list;
    }

    public boolean contains(LottoNumber number) {
        return numbers.contains(number);
    }

    public int countMatches(LottoNumbers other) {
        final List<Integer> otherNumbers = other.getNumbers();
        return (int) numbers.stream()
            .map(LottoNumber::value)
            .filter(otherNumbers::contains)
            .count();
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
