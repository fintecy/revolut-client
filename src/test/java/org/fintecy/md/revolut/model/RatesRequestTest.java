package org.fintecy.md.revolut.model;

import org.junit.jupiter.api.Test;

import static org.fintecy.md.revolut.model.Currency.currency;
import static org.fintecy.md.revolut.model.RatesRequest.request;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RatesRequestTest {
    @Test
    void should_create_request() {
        var actual = request().from(currency("USD")).to(currency("GBP")).build();
        var expected = request().from("USD/GBP").build();
        assertEquals(actual, expected);
    }
}