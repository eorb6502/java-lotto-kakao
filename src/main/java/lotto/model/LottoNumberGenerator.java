package lotto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LottoNumberGenerator {
    private static final List<Integer> NUMBER_POOL = createNumberPool();
    private final Random random;

    public LottoNumberGenerator() {
        this.random = new Random();
    }

    public LottoNumberGenerator(long seed) {
        this.random = new Random(seed);
    }

    public PurchasedLottoNumbers generate() {
        List<Integer> candidates = new ArrayList<>(NUMBER_POOL);
        Collections.shuffle(candidates, random);
        List<Integer> generated = new ArrayList<>(candidates.subList(0, LottoNumbers.getLottoSize()));
        return new PurchasedLottoNumbers(generated);
    }

    public List<PurchasedLottoNumbers> generate(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("생성 개수는 0 이상이어야 합니다.");
        }
        List<PurchasedLottoNumbers> list = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            list.add(generate());
        }
        return list;
    }

    private static List<Integer> createNumberPool() {
        List<Integer> candidates = new ArrayList<>();
        for (int number = LottoNumber.MIN_NUMBER; number <= LottoNumber.MAX_NUMBER; number++) {
            candidates.add(number);
        }
        return List.copyOf(candidates);
    }
}
