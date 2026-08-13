package com.inventory_management.security.ratelimit;

public record RateLimitPolicy(
        String name,
        long capacity,
        long refillTokens,
        long refillIntervalMillis
) {

}