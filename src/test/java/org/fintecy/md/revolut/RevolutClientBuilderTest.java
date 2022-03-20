package org.fintecy.md.revolut;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.failsafe.RateLimiter;
import org.junit.jupiter.api.Test;

import static java.net.http.HttpClient.newHttpClient;
import static java.time.Duration.ofSeconds;
import static org.fintecy.md.revolut.RevolutApi.ROOT_PATH;
import static org.fintecy.md.revolut.RevolutClient.revolutClient;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RevolutClientBuilderTest {

    @Test
    void should_build_same_request() {
        var rateLimiter = RateLimiter.smoothBuilder(ofSeconds(1)).build();
        var expected = RevolutClient.managedApi(rateLimiter);
        var actual = revolutClient()
                .useClient(newHttpClient())
                .mapper(new ObjectMapper())
                .rootPath(ROOT_PATH)
                .with(rateLimiter)
                .build();
        assertEquals(actual, expected);
    }
}