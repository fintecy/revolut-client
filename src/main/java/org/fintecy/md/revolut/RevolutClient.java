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
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static org.fintecy.md.revolut.model.Currency.currency;

public class RevolutClient implements RevolutApi {
    private final String rootPath;
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final List<Policy<?>> policies;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        api()
                .currencies().thenApply(currencies ->
                        currencies.stream().map(currency -> {
                                    try {
                                        return api().latest(currency, currency("USD")).get();
                                    } catch (InterruptedException | ExecutionException e) {
                                        return null;
                                    }
                                })
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList()))
                .get()
                .stream()
                .sorted(comparing(ExchangeRate::getTimestamp))
                .forEach(System.out::println);
    }

    protected RevolutClient(String rootPath, ObjectMapper mapper, HttpClient httpClient, List<Policy<?>> policies) {
        this.client = checkRequired(httpClient, "Http client required for Revolut client");
        this.mapper = checkRequired(mapper, "object mapper is required for serialization");
        this.rootPath = checkRequired(rootPath, "root path cannot be empty");
        this.policies = ofNullable(policies).orElse(List.of());
    }

    public static RevolutApi managedApi(Policy... policies) {
        return new RevolutClientBuilder().with(policies).build();
    }

    public static RevolutApi api() {
        return new RevolutClientBuilder().build();
    }

    public static RevolutClientBuilder revolutClient() {
        return new RevolutClientBuilder();
    }

    public static <T> T checkRequired(T v, String msg) {
        return ofNullable(v)
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    @Override
    public CompletableFuture<ExchangeRate> latest(RatesRequest request) {
        var uri = URI.create(rootPath
                + "/exchange/quote/"
                + "?country=" + request.getCountry()
                + "&amount=" + request.getAmount()
                + "&fromCurrency=" + request.getFrom().getCode()
                + "&toCurrency=" + request.getTo().getCode()
                + "&isRecipientAmount=" + request.isRecipientAmount()
        );
        var httpRequest = HttpRequest.newBuilder()
                .setHeader("accept-language", "en-GB,en-US;q=0.9")
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
            throw new IllegalStateException("Can parse response: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (RevolutClient) o;
        return Objects.equals(rootPath, that.rootPath)
                && Objects.equals(policies, that.policies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootPath, policies);
    }
}
