package lotto.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputHistoryViewTest {
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    private void setInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
        System.out.print(data);
    }

    @Test
    @DisplayName("당첨 번호는 6개가 입력될 때까지 재시도")
    void test_input_winning_numbers_retries_on_invalid_count() {
        setInput("1,2,3,4,5\n1,2,3,4,5,6\n");

        InputHistoryView inputView = new InputHistoryView();
        List<Integer> winningNumbers = inputView.inputWinningNumbers();

        assertEquals(List.of(1, 2, 3, 4, 5, 6), winningNumbers);
    }

    @Test
    @DisplayName("보너스 번호는 숫자만 허용되며 유효 값이 입력될 때까지 재시도")
    void test_input_bonus_number_retries_on_invalid_input() {
        setInput("abc\n7\n");

        InputHistoryView inputView = new InputHistoryView();
        int bonusNumber = inputView.inputBonusNumber();

        assertEquals(7, bonusNumber);
    }

    @Test
    @DisplayName("보너스 번호가 범위를 벗어나면 재시도")
    void test_input_bonus_number_retries_on_out_of_range() {
        setInput("46\n7\n");

        InputHistoryView inputView = new InputHistoryView();
        int bonusNumber = inputView.inputBonusNumber();

        assertEquals(7, bonusNumber);
    }

    @Test
    @DisplayName("당첨 번호가 범위를 벗어나면 재시도")
    void test_input_winning_numbers_retries_on_out_of_range() {
        setInput("1,2,3,4,5,46\n1,2,3,4,5,6\n");

        InputHistoryView inputView = new InputHistoryView();
        List<Integer> winningNumbers = inputView.inputWinningNumbers();

        assertEquals(List.of(1, 2, 3, 4, 5, 6), winningNumbers);
    }

    @Test
    @DisplayName("당첨 번호가 중복되면 재시도")
    void test_input_winning_numbers_retries_on_duplicate() {
        setInput("1,2,3,4,5,5\n1,2,3,4,5,6\n");

        InputHistoryView inputView = new InputHistoryView();
        List<Integer> winningNumbers = inputView.inputWinningNumbers();

        assertEquals(List.of(1, 2, 3, 4, 5, 6), winningNumbers);
    }

    @Test
    @DisplayName("당첨 번호는 콤마+공백 형식도 허용한다")
    void test_input_winning_numbers_accepts_comma_with_space() {
        setInput("1, 2, 3, 4, 5, 6\n");

        InputHistoryView inputView = new InputHistoryView();
        List<Integer> winningNumbers = inputView.inputWinningNumbers();

        assertEquals(List.of(1, 2, 3, 4, 5, 6), winningNumbers);
    }
}
