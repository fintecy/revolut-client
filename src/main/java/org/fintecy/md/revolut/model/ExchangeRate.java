package org.fintecy.md.revolut.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import static java.math.BigDecimal.valueOf;

public class ExchangeRate {
    private final Currency base;
    private final Currency counter;
    private final Instant timestamp;
    private final BigDecimal ask;
    private final BigDecimal bid;
    private final BigDecimal mid;

    public ExchangeRate(Currency base, Currency counter, Instant timestamp,
                        BigDecimal ask,
                        BigDecimal mid,
                        BigDecimal bid) {
        this.base = base;
        this.counter = counter;
        this.timestamp = timestamp;
        this.ask = ask;
        this.mid = mid;
        this.bid = bid;
    }

    public static ExchangeRate exchangeRate(Currency base, Currency counter, Instant ts, BigDecimal mid) {
        return new ExchangeRate(base, counter, ts, mid, mid, mid);
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public BigDecimal getMid() {
        return mid;
    }

    public BigDecimal getBid() {
        return bid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(base, that.base) && Objects.equals(counter, that.counter)
                && Objects.equals(timestamp, that.timestamp) && Objects.equals(ask, that.ask)
                && Objects.equals(bid, that.bid) && Objects.equals(mid, that.mid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, counter, timestamp, ask, bid, mid);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "base=" + base +
                ", counter=" + counter +
                ", timestamp=" + timestamp +
                ", ask=" + ask +
                ", bid=" + bid +
                ", mid=" + mid +
                '}';
    }
}
