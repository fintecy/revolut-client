package org.fintecy.md.revolut;

import org.fintecy.md.revolut.model.ExchangeRate;
import org.fintecy.md.revolut.model.RatesRequest;

import java.util.concurrent.CompletableFuture;

/**
 * Available for testing purposes
 */
public class NoOpRevolutApi implements RevolutApi {
    @Override
    public CompletableFuture<ExchangeRate> latest(RatesRequest params) {
        throw new IllegalStateException("not implemented");
    }
}
