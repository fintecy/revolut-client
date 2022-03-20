package org.fintecy.md.revolut.model;

import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;
import static java.time.Instant.now;
import static org.fintecy.md.revolut.model.Currency.currency;
import static org.fintecy.md.revolut.model.ExchangeRate.exchangeRate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangeRateTest {

    @Test
    void should_create_correct_mid_rate() {
        var bid = valueOf(1.3);
        var mid = valueOf(1.2);
        var ask = valueOf(1.1);
        var exchangeRate = new ExchangeRate(currency("GBP"), currency("USD"), now(), ask, bid);
        assertEquals(exchangeRate.getAsk(), ask);
        assertEquals(exchangeRate.getBid(), bid);
        assertEquals(exchangeRate.getMid(), mid);
    }

    @Test
    void should_create_correct_exchange_rate() {
        var mid = valueOf(1.2);
        var exchangeRate = exchangeRate(currency("GBP"), currency("USD"), now(), mid);
        assertEquals(exchangeRate.getAsk(), mid);
        assertEquals(exchangeRate.getBid(), mid);
        assertEquals(exchangeRate.getMid(), mid);
    }

    @Test
    void should_validate_to_string() {
        var now = now();
        var exchangeRate = exchangeRate(currency("GBP"), currency("USD"), now, valueOf(1.2));
        assertEquals("ExchangeRate{base=Currency(GBP), counter=Currency(USD), " +
                        "timestamp=" + now + ", ask=1.2, bid=1.2, mid=1.2}",
                exchangeRate.toString());
    }

    @Test
    void should_validate_equals() {
        var bid = valueOf(1.3);
        var mid = valueOf(1.2);
        var ask = valueOf(1.1);
        var now = now();
        var first = new ExchangeRate(currency("GBP"), currency("USD"), now, ask, bid);
        var second = new ExchangeRate(currency("GBP"), currency("USD"), now, ask, mid, bid);
        assertEquals(first, second);
        assertEquals(first, second);
    }
}