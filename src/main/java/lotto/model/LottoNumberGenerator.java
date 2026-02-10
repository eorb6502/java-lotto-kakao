package lotto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LottoNumberGenerator {
    private static final int LOTTO_MIN_NUMBER = 1;
    private static final int LOTTO_MAX_NUMBER = 45;
    private static final int LOTTO_SIZE = 6;

    private final Random random;

    public LottoNumberGenerator() {
        this.random = new Random();
    }

    public LottoNumberGenerator(long seed) {
        this.random = new Random(seed);
    }

    public LottoNumbers generate() {
        List<Integer> candidates = createCandidates();
        Collections.shuffle(candidates, random);

        List<Integer> generatedNumbers = new ArrayList<>(candidates.subList(0, LOTTO_SIZE));
        Collections.sort(generatedNumbers);
        return new LottoNumbers(generatedNumbers);
    }

    public List<LottoNumbers> generate(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("생성 개수는 0 이상이어야 합니다.");
        }
        List<LottoNumbers> generatedLottos = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            generatedLottos.add(generate());
        }
        return generatedLottos;
    }

    private List<Integer> createCandidates() {
        List<Integer> candidates = new ArrayList<>();
        for (int number = LOTTO_MIN_NUMBER; number <= LOTTO_MAX_NUMBER; number++) {
            candidates.add(number);
        }
        return candidates;
    }
}
