package org.fintecy.md.revolut.model;

import java.math.BigDecimal;
import java.util.Objects;

public class RatesRequest {
    private final Currency from;
    private final Currency to;
    private final BigDecimal amount;
    private final boolean isRecipientAmount;
    private final String country;

    public RatesRequest(Currency from, Currency to, BigDecimal amount, boolean isRecipientAmount, String country) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.isRecipientAmount = isRecipientAmount;
        this.country = country;
    }

    public static RatesRequestBuilder request() {
        return new RatesRequestBuilder();
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isRecipientAmount() {
        return isRecipientAmount;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatesRequest that = (RatesRequest) o;
        return isRecipientAmount == that.isRecipientAmount && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(amount, that.amount) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, amount, isRecipientAmount, country);
    }

    @Override
    public String toString() {
        return "RatesRequest{" +
                "from=" + from +
                ", to=" + to +
                ", amount=" + amount +
                ", isRecipientAmount=" + isRecipientAmount +
                ", country='" + country + '\'' +
                '}';
    }

    public static class RatesRequestBuilder {
        private Currency from = Currency.currency("USD");
        private Currency to;
        private BigDecimal amount = BigDecimal.valueOf(100_00);
        private boolean isRecipientAmount = false;
        private String country = "GB";

        RatesRequestBuilder() {
        }

        public RatesRequestBuilder from(Currency from) {
            this.from = from;
            return this;
        }

        public RatesRequestBuilder to(Currency to) {
            this.to = to;
            return this;
        }

        public RatesRequestBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public RatesRequestBuilder isRecipientAmount(boolean isRecipientAmount) {
            this.isRecipientAmount = isRecipientAmount;
            return this;
        }

        public RatesRequestBuilder country(String country) {
            this.country = country;
            return this;
        }

        public RatesRequest build() {
            return new RatesRequest(from, to, amount, isRecipientAmount, country);
        }

        public String toString() {
            return "RatesRequest.RatesRequestBuilder(from=" + this.from + ", to=" + this.to + ", amount=" + this.amount + ", isRecipientAmount=" + this.isRecipientAmount + ", country=" + this.country + ")";
        }
    }
}
