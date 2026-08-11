package com.inventory_management.security.ratelimit;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, TokenBucket> buckets =
            new ConcurrentHashMap<>();

    private final RateLimitConfig config;

    public RateLimitService(RateLimitConfig config) {
        this.config = config;
    }

    public boolean isAllowed(String clientIp) {

        TokenBucket bucket = buckets.computeIfAbsent(
                clientIp,
                ip -> new TokenBucket(
                        config.getCapacity(),
                        config.getRefillTokens(),
                        config.getRefillIntervalMs()
                )
        );

        return bucket.tryConsume();
    }
}