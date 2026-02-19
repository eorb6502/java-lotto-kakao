package lotto.model;

import java.util.*;

public class LottoNumbers {
    private static final int LOTTO_SIZE = 6;
    private static final String NULL_NUMBERS_MESSAGE = "로또 번호 목록은 비어 있을 수 없습니다.";
    private static final String NULL_NUMBER_ELEMENT_MESSAGE = "로또 번호에는 null이 포함될 수 없습니다.";
    private static final String NULL_OTHER_MESSAGE = "비교 대상 로또 번호는 비어 있을 수 없습니다.";

    private final List<LottoNumber> numbers;

    public LottoNumbers(List<Integer> numbers) {
        validateNumbersNotNull(numbers);
        List<LottoNumber> convertedNumbers = convertNumbers(numbers);
        validateSize(convertedNumbers);
        Set<LottoNumber> numberSet = new HashSet<>(convertedNumbers);
        validateDistinct(numberSet);
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

    private void validateDistinct(Set<LottoNumber> set) {
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
        validateOtherNotNull(other);
        return (int) numbers.stream()
            .filter(other::contains)
            .count();
    }

    private void validateNumbersNotNull(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException(NULL_NUMBERS_MESSAGE);
        }
    }

    private void validateOtherNotNull(LottoNumbers other) {
        if (other == null) {
            throw new IllegalArgumentException(NULL_OTHER_MESSAGE);
        }
    }

    private static List<LottoNumber> convertNumbers(List<Integer> numbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            validateNumberNotNull(number);
            lottoNumbers.add(new LottoNumber(number));
        }

        return lottoNumbers;
    }

    private static void validateNumberNotNull(Integer number) {
        if (number == null) {
            throw new IllegalArgumentException(NULL_NUMBER_ELEMENT_MESSAGE);
        }
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
