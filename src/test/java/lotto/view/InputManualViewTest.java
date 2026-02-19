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
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputManualViewTest {
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("수동 구매 개수는 유효할 때까지 재시도")
    void test_input_manual_count_retries_on_invalid_input() {
        setInput("abc\n3\n");

        InputManualView inputView = new InputManualView();
        int manualCount = inputView.inputManualCount(5);

        assertEquals(3, manualCount);
    }

    @Test
    @DisplayName("수동 구매 개수는 최대 구매 수를 넘을 수 없다")
    void test_input_manual_count_maximum() {
        setInput("6\n5\n");

        InputManualView inputView = new InputManualView();
        int manualCount = inputView.inputManualCount(5);

        assertEquals(5, manualCount);
    }

    @Test
    @DisplayName("수동 구매 개수가 0이면 빈 목록 반환")
    void test_input_manual_lottos_zero() {
        InputManualView inputView = new InputManualView();
        List<List<Integer>> manualLottos = inputView.inputManualLottos(0);

        assertEquals(0, manualLottos.size());
    }

    @Test
    @DisplayName("수동 로또 번호는 유효할 때까지 재시도")
    void test_input_manual_lottos_retries_on_invalid_input() {
        setInput("1,2,3,4,5\n1,2,3,4,5,6\n");

        InputManualView inputView = new InputManualView();
        List<List<Integer>> manualLottos = inputView.inputManualLottos(1);

        assertEquals(1, manualLottos.size());
        assertEquals(List.of(1, 2, 3, 4, 5, 6), manualLottos.get(0));
    }

    @Test
    @DisplayName("수동 로또 번호는 콤마+공백 형식도 허용한다")
    void test_input_manual_lottos_accepts_comma_with_space() {
        setInput("1, 2, 3, 4, 5, 6\n");

        InputManualView inputView = new InputManualView();
        List<List<Integer>> manualLottos = inputView.inputManualLottos(1);

        assertEquals(List.of(1, 2, 3, 4, 5, 6), manualLottos.get(0));
    }

    @Test
    @DisplayName("수동 로또 번호가 중복되면 유효할 때까지 재시도한다")
    void test_input_manual_lottos_retries_on_duplicated_numbers() {
        setInput("1,1,2,3,4,5\n1,2,3,4,5,6\n");

        InputManualView inputView = new InputManualView();
        List<List<Integer>> manualLottos = inputView.inputManualLottos(1);

        assertEquals(List.of(1, 2, 3, 4, 5, 6), manualLottos.get(0));
    }

    @Test
    @DisplayName("입력 스캐너가 null이면 예외가 발생한다")
    void test_constructor_null_scanner() {
        assertThrows(IllegalArgumentException.class, () -> new InputManualView(null));
    }

    private void setInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
    }
}
