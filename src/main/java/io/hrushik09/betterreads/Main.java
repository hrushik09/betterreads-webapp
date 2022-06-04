package io.hrushik09.betterreads;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Paths;

public class Main {
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private static String id;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private static String secret;

    @Value("${spring.data.cassandra.keyspace-name}")
    private static String keyspaceName;

    @Value("${datastax.astra.secure-connect-bundle}")
    private static String secureConnectBundle;

    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(secureConnectBundle))
                .withAuthCredentials(id, secret)
                .withKeyspace(keyspaceName)
                .build()) {
        }
    }
}
