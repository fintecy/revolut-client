package org.fintecy.md.revolut;

import org.fintecy.md.revolut.model.Currency;
import org.fintecy.md.revolut.model.ExchangeRate;
import org.fintecy.md.revolut.model.RatesRequest;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.fintecy.md.revolut.model.Currency.currency;
import static org.fintecy.md.revolut.model.RatesRequest.request;

/**
 * @author batiaev
 * @see <a href="https://revolut.com/currencies">docs</a>
 */
public interface RevolutApi {
    Set<Currency> SUPPORTED_CURRENCIES = Set.of(//from company website selector
            currency("GBP"), currency("EUR"), currency("USD"), currency("BTC"), currency("ETH"), currency("SOL"),
            currency("AED"), currency("ALL"), currency("ADA"), currency("AMP"), currency("APE"), currency("BAL"),
            currency("AMD"), currency("ANG"), currency("AOA"), currency("ARS"), currency("AUD"), currency("AZN"),
            currency("BAT"), currency("BCH"), currency("BNT"), currency("DOT"), currency("BAM"), currency("BBD"),
            currency("BDT"), currency("BGN"), currency("BHD"), currency("BMD"), currency("BND"), currency("BOB"),
            currency("BRL"), currency("BSD"), currency("BWP"), currency("BYN"), currency("BZD"), currency("CHZ"),
            currency("CLV"), currency("CAD"), currency("CHF"), currency("CLP"), currency("CNH"),
            currency("CNY"), currency("COP"), currency("CRC"), currency("CZK"), currency("DJF"), currency("DKK"),
            currency("DOP"), currency("DZD"), currency("EGP"), currency("ETB"), currency("FJD"), currency("FKP"),
            currency("GEL"), currency("GGP"), currency("GHS"), currency("GIP"), currency("GTQ"), currency("GYD"),
            currency("HKD"), currency("HNL"), currency("HRK"), currency("HTG"), currency("HUF"), currency("IDR"),
            currency("ILS"), currency("IMP"), currency("INR"), currency("ISK"), currency("JEP"), currency("JMD"),
            currency("JOD"), currency("JPY"), currency("KES"), currency("KGS"), currency("KHR"), currency("KRW"),
            currency("KWD"), currency("KZT"), currency("LAK"), currency("LBP"), currency("LKR"), currency("MAD"),
            currency("MDL"), currency("MKD"), currency("MMK"), currency("MOP"), currency("MUR"), currency("MWK"),
            currency("MXN"), currency("MYR"), currency("MZN"), currency("NAD"), currency("NGN"), currency("NIO"),
            currency("NOK"), currency("NPR"), currency("NZD"), currency("PLN"), currency("PEN"), currency("PGK"),
            currency("PHP"), currency("PKR"), currency("PYG"), currency("QAR"), currency("RON"), currency("RSD"),
            currency("RUB"), currency("RWF"), currency("SAR"), currency("SEK"), currency("SGD"), currency("THB"),
            currency("TND"), currency("TRY"), currency("TTD"), currency("TWD"), currency("TZS"), currency("UAH"),
            currency("UYU"), currency("UZS"), currency("VES"), currency("VND"), currency("VUV"), currency("LTC"),
            currency("XAF"), currency("XCD"), currency("XOF"), currency("XPF"), currency("XAU"), currency("XAG"),
            currency("ZAR"), currency("ZMW"), currency("ZRX")
    );
    String ROOT_PATH = "https://www.revolut.com/api";

    default CompletableFuture<ExchangeRate> latest(Currency base, Currency counter) {
        return latest(request().from(base).to(counter).build());
    }

    default CompletableFuture<ExchangeRate> latest(String pair) {
        return latest(request().from(pair).build());
    }


    /**
     * @param params - request params
     * @return exchange rate
     * @see <a href="https://revolut.com/api/exchange/quote?amount=10000&country=GB&fromCurrency=USD&isRecipientAmount=false&toCurrency=EUR">test request</a>
     */
    CompletableFuture<ExchangeRate> latest(RatesRequest params);

    default CompletableFuture<Set<Currency>> currencies() {
        return CompletableFuture.completedFuture(SUPPORTED_CURRENCIES);
    }
}
