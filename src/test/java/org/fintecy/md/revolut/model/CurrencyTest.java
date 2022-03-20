package org.fintecy.md.revolut.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static java.util.Currency.getInstance;
import static org.fintecy.md.revolut.model.Currency.currency;
import static org.fintecy.md.revolut.model.Currency.fromJavaCurrency;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyTest {
    private static Set<java.util.Currency> getAvailableCurrencies() {
        return java.util.Currency.getAvailableCurrencies();
    }

    @Test
    void should_compare_currencies() {
        var first = currency("GBP");
        var seconds = fromJavaCurrency(getInstance("GBP"));
        assertEquals(0, first.compareTo(seconds));
    }

    @ParameterizedTest
    @MethodSource("getAvailableCurrencies")
    void shouldConvertJavaCurrencies(java.util.Currency currency) {
        java.util.Currency actual = fromJavaCurrency(currency).toJavaCurrency();
        assertEquals(currency, actual);
        System.out.println(currency);
    }
}