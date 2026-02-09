package lotto.controller;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoNumbers;
import lotto.model.LottoResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppProfitRateTest {
    private static final int LOTTO_PRICE = 1000;
    private static final Map<LottoResult, Long> PRIZE_BY_RESULT = Map.of(
        LottoResult.RANK_FIRST, 2_000_000_000L,
        LottoResult.RANK_SECOND, 30_000_000L,
        LottoResult.RANK_THIRD, 1_500_000L,
        LottoResult.RANK_FOURTH, 50_000L,
        LottoResult.RANK_FIFTH, 5_000L,
        LottoResult.RANK_NONE, 0L
    );

    @Test
    @DisplayName("seed 0 기반 생성 결과에서 수익률 계산이 dot 연산과 일치")
    void test_profit_rate_with_seed_zero() throws Exception {
        int ticketCount = 20;
        int price = ticketCount * LOTTO_PRICE;

        LottoNumberGenerator generator = new LottoNumberGenerator(0L);
        List<LottoNumbers> generatedNumbers = generator.generate(ticketCount);
        LottoNumbers winningNumbers = createWinningNumbers(generatedNumbers.getFirst());

        Map<LottoResult, Integer> resultCountByRank = invokeCountByRank(winningNumbers, generatedNumbers);

        int totalCount = resultCountByRank.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        assertEquals(ticketCount, totalCount);

        double actualProfitRate = invokeCalculateProfitRate(price, resultCountByRank);
        double expectedProfitRate = calculateExpectedProfitRate(price, resultCountByRank);
        assertEquals(expectedProfitRate, actualProfitRate, 1e-12);
    }

    @SuppressWarnings("unchecked")
    private Map<LottoResult, Integer> invokeCountByRank(LottoNumbers winningNumbers, List<LottoNumbers> generatedNumbers)
        throws Exception {
        Method method = App.class.getDeclaredMethod("countByRank", LottoNumbers.class, List.class);
        method.setAccessible(true);
        return (Map<LottoResult, Integer>) method.invoke(null, winningNumbers, generatedNumbers);
    }

    private double invokeCalculateProfitRate(int price, Map<LottoResult, Integer> resultCountByRank)
        throws Exception {
        Method method = App.class.getDeclaredMethod("calculateProfitRate", int.class, Map.class);
        method.setAccessible(true);
        return (double) method.invoke(null, price, resultCountByRank);
    }

    private LottoNumbers createWinningNumbers(LottoNumbers baseWinningNumbers) {
        List<Integer> winningNumbers = baseWinningNumbers.getNumbers();
        int bonusNumber = findBonusNumberNotIn(winningNumbers);
        return new LottoNumbers(winningNumbers, bonusNumber);
    }

    private int findBonusNumberNotIn(List<Integer> winningNumbers) {
        for (int number = 1; number <= 45; number++) {
            if (!winningNumbers.contains(number)) {
                return number;
            }
        }
        throw new IllegalStateException("보너스 번호를 찾을 수 없습니다.");
    }

    private double calculateExpectedProfitRate(int price, Map<LottoResult, Integer> resultCountByRank) {
        long totalPrize = 0L;
        for (Map.Entry<LottoResult, Integer> entry : resultCountByRank.entrySet()) {
            long prize = PRIZE_BY_RESULT.getOrDefault(entry.getKey(), 0L);
            totalPrize += prize * entry.getValue();
        }
        return (double) totalPrize / price;
    }
}
