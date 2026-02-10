package lotto.view;

import lotto.model.LottoResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OutputViewTest {
    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("당첨 통계 출력에 핵심 문구가 포함된다")
    void test_print_statistics_contains_expected_lines() {
        OutputView outputView = new OutputView();
        Map<LottoResult, Integer> resultCountByRank = Map.of(
            LottoResult.RANK_FIFTH, 1,
            LottoResult.RANK_FOURTH, 2,
            LottoResult.RANK_THIRD, 3,
            LottoResult.RANK_SECOND, 4,
            LottoResult.RANK_FIRST, 5
        );

        outputView.printStatistics(resultCountByRank, 1.23);

        String output = outputStream.toString();
        assertTrue(output.contains("당첨 통계"));
        assertTrue(output.contains("3개 일치"));
        assertTrue(output.contains("4개 일치"));
        assertTrue(output.contains("5개 일치 ("));
        assertTrue(output.contains("5개 일치, 보너스 볼 일치"));
        assertTrue(output.contains("6개 일치"));
        assertTrue(output.contains("총 수익률은 1.23입니다."));
    }
}
