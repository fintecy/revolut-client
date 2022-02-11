package org.fintecy.md.revolut;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.fintecy.md.revolut.RevolutApi.SUPPORTED_CURRENCIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NoOpRevolutApiTest {
    private RevolutApi noOpRevolutApi;

    @BeforeEach
    void setUp() {
        noOpRevolutApi = new NoOpRevolutApi();
    }

    @Test
    void should_throw_exception_for_latest() {
        assertThrows(IllegalStateException.class, () -> noOpRevolutApi.latest("USD/GBP").get());
    }

    @Test
    void should_throw_exception_for_currencies() throws ExecutionException, InterruptedException {
        var currencies = noOpRevolutApi.currencies().get();
        assertEquals(currencies, SUPPORTED_CURRENCIES);
    }
}