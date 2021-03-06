package org.fintecy.md.revolut;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.fintecy.md.revolut.model.Currency;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.math.BigDecimal.valueOf;
import static java.time.Instant.ofEpochMilli;
import static org.fintecy.md.revolut.RevolutApi.SUPPORTED_CURRENCIES;
import static org.fintecy.md.revolut.RevolutClient.api;
import static org.fintecy.md.revolut.RevolutClient.revolutClient;
import static org.fintecy.md.revolut.model.Currency.currency;
import static org.fintecy.md.revolut.model.ExchangeRate.exchangeRate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(httpPort = 7777)
class RevolutClientTest {
    @Test
    void should_create_api() {
        var actual = api();
        var expected = RevolutClient.revolutClient().build();
        assertEquals(expected.hashCode(), actual.hashCode());
        assertEquals(expected, actual);
    }

    @Test
    void should_return_latest() throws ExecutionException, InterruptedException {
        Currency base = currency("USD");
        Currency counter = currency("GBP");
        stubFor(get("/exchange/quote?country=GB" +
                "&amount=10000" +
                "&fromCurrency=" + base.getCode() +
                "&toCurrency=" + counter.getCode() +
                "&isRecipientAmount=false")
                .willReturn(aResponse()
                        .withBodyFile("latest.json")));

        var expected = exchangeRate(base, counter, ofEpochMilli(1644537189000L), valueOf(0.73765244));
        var actual = revolutClient()
                .rootPath("http://localhost:7777")
                .build()
                .latest("USD/GBP")
                .get();
        assertEquals(expected, actual);
    }

    @Test
    void should_return_latest_using_two_currency() throws ExecutionException, InterruptedException {
        Currency base = currency("USD");
        Currency counter = currency("GBP");
        stubFor(get("/exchange/quote?country=GB" +
                "&amount=10000" +
                "&fromCurrency=" + base.getCode() +
                "&toCurrency=" + counter.getCode() +
                "&isRecipientAmount=false")
                .willReturn(aResponse()
                        .withBodyFile("latest.json")));

        var expected = exchangeRate(base, counter, ofEpochMilli(1644537189000L), valueOf(0.73765244));
        var actual = revolutClient()
                .rootPath("http://localhost:7777")
                .build()
                .latest(base, counter)
                .get();
        assertEquals(expected, actual);
    }

    @Test
    void should_return_currencies() throws ExecutionException, InterruptedException {
        var actual = revolutClient()
                .rootPath("http://localhost:7777")
                .build()
                .currencies()
                .get();
        assertEquals(SUPPORTED_CURRENCIES, actual);
    }

    @Test
    void should_fail_on_invalid_currency_pair() {
        assertThrows(IllegalArgumentException.class, () -> revolutClient()
                .rootPath("http://localhost:7777")
                .build()
                .latest("USD/EUR/GBP")
                .get());
    }
}