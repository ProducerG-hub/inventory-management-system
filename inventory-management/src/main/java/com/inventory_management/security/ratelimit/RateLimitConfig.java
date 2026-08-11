package com.inventory_management.security.ratelimit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.rate-limit")
public class RateLimitConfig {

    private long capacity;
    private long refillTokens;
    private long refillIntervalMs;

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getRefillTokens() {
        return refillTokens;
    }

    public void setRefillTokens(long refillTokens) {
        this.refillTokens = refillTokens;
    }

    public long getRefillIntervalMs() {
        return refillIntervalMs;
    }

    public void setRefillIntervalMs(long refillIntervalMs) {
        this.refillIntervalMs = refillIntervalMs;
    }
}