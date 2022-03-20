package org.fintecy.md.revolut;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.failsafe.Policy;
import org.fintecy.md.revolut.serialization.RevolutModule;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class RevolutClientBuilder {
    private ObjectMapper mapper = new ObjectMapper().registerModule(new RevolutModule());
    private HttpClient client = HttpClient.newHttpClient();
    private List<Policy<?>> policies = new ArrayList<>();
    private String rootPath = RevolutApi.ROOT_PATH;

    public RevolutClientBuilder useClient(HttpClient client) {
        this.client = client;
        return this;
    }

    public RevolutClientBuilder mapper(ObjectMapper mapper) {
        this.mapper = mapper.registerModule(new RevolutModule());
        return this;
    }

    public RevolutClientBuilder rootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public RevolutClientBuilder with(Policy<?>... policies) {
        this.policies.addAll(asList(policies));
        return this;
    }

    public RevolutClientBuilder with(Policy policy) {
        this.policies.add(policy);
        return this;
    }

    public RevolutApi build() {
        return new RevolutClient(rootPath, mapper, client, policies);
    }
}
