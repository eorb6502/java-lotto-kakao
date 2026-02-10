package lotto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LottoNumberGenerator {
    private static final int LOTTO_SIZE = 6;

    private final Random random;

    public LottoNumberGenerator() {
        this.random = new Random();
    }

    public LottoNumberGenerator(long seed) {
        this.random = new Random(seed);
    }

    public LottoNumbers generate() {
        // 1 ~ 45 숫자 리스트를 생성하고 랜덤하게 섞기
        final List<Integer> candidates = new ArrayList<>();
        for (int number = LottoNumber.MIN_NUMBER; number <= LottoNumber.MAX_NUMBER; number++) {
            candidates.add(number);
        }
        Collections.shuffle(candidates, random);

        // 랜덤한 리스트의 앞 6개 숫자를 추출하고 정렬
        List<Integer> generated = new ArrayList<>(candidates.subList(0, LOTTO_SIZE));
        Collections.sort(generated);
        return new LottoNumbers(generated);
    }

    public List<LottoNumbers> generate(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("생성 개수는 0 이상이어야 합니다.");
        }
        List<LottoNumbers> list = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            list.add(generate());
        }
        return list;
    }
}
