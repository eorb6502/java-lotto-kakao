package lotto.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputPriceViewTest {
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
    @DisplayName("구입금액은 숫자만 허용되며 유효 값이 입력될 때까지 재시도")
    void test_input_price_retries_on_invalid_input() {
        setInput("abc\n1000\n");

        InputPriceView inputView = new InputPriceView(1000);
        int price = inputView.inputPrice();

        assertEquals(1000, price);
    }

    @Test
    @DisplayName("구입금액은 1000원 이상이어야 한다")
    void test_input_price_minimum() {
        setInput("999\n1000\n");

        InputPriceView inputView = new InputPriceView(1000);
        int price = inputView.inputPrice();

        assertEquals(1000, price);
    }

    private void setInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
    }
}
