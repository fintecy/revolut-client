package org.fintecy.md.revolut.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.fintecy.md.revolut.model.ExchangeRate;

import java.io.IOException;
import java.util.Set;

import static java.time.Instant.ofEpochMilli;
import static java.util.Optional.ofNullable;
import static org.fintecy.md.revolut.model.Currency.currency;
import static org.fintecy.md.revolut.model.ExchangeRate.exchangeRate;

public class ExchangeRateDeserializer extends StdDeserializer<ExchangeRate> {
    public final static ExchangeRateDeserializer INSTANCE = new ExchangeRateDeserializer();
    public static final Set<String> REQUIRED_RATE_FIELDS = Set.of("from", "to", "rate", "timestamp");

    public ExchangeRateDeserializer() {
        super(ExchangeRate.class);
    }

    @Override
    public ExchangeRate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonNode node = jp.getCodec().readTree(jp);
        JsonNode rateNode = ofNullable(node.get("rate")).orElseThrow(() -> new IllegalStateException("rate is not provided"));

        for (String field : REQUIRED_RATE_FIELDS) {
            if (!rateNode.has(field)) throw new IllegalStateException("Required field " + field + " is missing");
        }

        return exchangeRate(currency(rateNode.get("from").asText()), currency(rateNode.get("to").asText()),
                ofEpochMilli(rateNode.get("timestamp").longValue()), rateNode.get("rate").decimalValue());
    }
}
