package org.fintecy.md.revolut;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.failsafe.Policy;
import org.fintecy.md.revolut.model.ExchangeRate;
import org.fintecy.md.revolut.model.RatesRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.Optional.ofNullable;

public class RevolutClient implements RevolutApi {
    private final String rootPath;
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final List<Policy<Object>> policies;

    protected RevolutClient(String rootPath, ObjectMapper mapper, HttpClient httpClient, List<Policy<Object>> policies) {
        this.client = checkRequired(httpClient, "Http client required for Revolut client");
        this.mapper = checkRequired(mapper, "object mapper is required for serialization");
        this.rootPath = checkRequired(rootPath, "root path cannot be empty");
        this.policies = ofNullable(policies).orElse(List.of());
    }

    public static RevolutApi api() {
        return new RevolutClientBuilder().build();
    }

    public static RevolutClientBuilder revolutClient() {
        return new RevolutClientBuilder();
    }

    public static double checkRequired(double v, String msg) {
        return (v == 0 ? Optional.<Double>empty() : Optional.of(v))
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    public static <T> T checkRequired(T v, String msg) {
        return ofNullable(v)
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    @Override
    public CompletableFuture<ExchangeRate> latest(RatesRequest request) {
        URI uri = URI.create(rootPath
                + "/exchange/quote"
                + "?country=" + request.getCountry()
                + "&amount=" + request.getAmount()
                + "&fromCurrency=" + request.getFrom().getCode()
                + "&toCurrency=" + request.getTo().getCode()
                + "&isRecipientAmount=" + request.isRecipientAmount()
        );
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        return client.sendAsync(httpRequest, ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseResponse);
    }

    private ExchangeRate parseResponse(String body) {
        try {
            return mapper.readValue(body, ExchangeRate.class);
        } catch (IOException e) {
            throw new IllegalStateException("Can parse response", e);
        }
    }
}
