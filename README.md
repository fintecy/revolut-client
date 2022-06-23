[![Build](https://github.com/fintecy/revolut-client/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/fintecy/revolut-client/actions/workflows/gradle.yml)

# Revolut FX rates Client

Async client with CompletableFutures based on new HttpClient (java 11+)

## Dependency
https://mvnrepository.com/artifact/org.fintecy.md/revolut-client/1.0.2

### Gradle
```
implementation 'org.fintecy.md:revolut-client:1.0.2'
```

### Maven
```
<dependency>
<groupId>org.fintecy.md</groupId>
<artifactId>revolut-client</artifactId>
<version>1.0.2</version>
</dependency>
```

## Usage
### Simple client creation
```
RevolutApi api = RevolutClient.api();
ExchangeRate latest = api.latest("USD/GBP").get();
```
### Complex client configuration
```
var client = revolutClient()
    .useClient(HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .priority(10)
        .connectTimeout(Duration.ofMillis(500))
        .executor(Executors.newSingleThreadExecutor())
        .build())
    .with(CircuitBreaker.ofDefaults())
    .with(RateLimiter.smoothBuilder(Duration.ofMillis(100))
        .build())
    .with(RetryPolicy.ofDefaults())
    .with(Timeout.of(Duration.ofMillis(400)))
    .rootPath("https://revolut.com/api/") -- just to use stub in tests
    .build();
```

## Dependencies
- Java 11+
- FailSafe
- Slf4j api
- Jackson (databind, datatype-jsr310)
- WireMock (tests)
- Junit5 (tests)

## Author
Anton Batiaev <anton@batiaev.com>
