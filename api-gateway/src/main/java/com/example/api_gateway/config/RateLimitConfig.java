package com.example.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Optional;

@Configuration
public class RateLimitConfig {
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            String clientIp = Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("X-Forwarded-For"))
                    .map(header -> header.split(",")[0].trim())
                    .filter(value -> !value.isEmpty())
                    .orElseGet(() -> {
                        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
                        if (remoteAddress != null && remoteAddress.getAddress() != null) {
                            return remoteAddress.getAddress().getHostAddress();
                        }
                        return "unknown";
                    });

            return Mono.just(clientIp);
        };
    }
}