package com.inventory_management.security.ratelimit;

public class TokenBucket {

    private final long capacity;
    private final long refillTokens;
    private final long refillIntervalMillis;

    private double tokens;
    private long lastRefillTimestamp;

    public TokenBucket(
            long capacity,
            long refillTokens,
            long refillIntervalMillis
    ) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillIntervalMillis = refillIntervalMillis;

        this.tokens = capacity;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean tryConsume() {

        refill();

        if (tokens >= 1) {
            tokens--;
            return true;
        }

        return false;
    }

    public synchronized long getRetryAfterSeconds() {

        refill();

        if (tokens >= 1) {
            return 0;
        }

        long now = System.currentTimeMillis();

        long elapsed = now - lastRefillTimestamp;

        long remainingMillis =
                refillIntervalMillis - elapsed;

        if (remainingMillis <= 0) {
            return 0;
        }

        return (long) Math.ceil(
                remainingMillis / 1000.0
        );
    }

    private void refill() {

        long now = System.currentTimeMillis();

        long elapsed = now - lastRefillTimestamp;

        if (elapsed < refillIntervalMillis) {
            return;
        }

        long intervals = elapsed / refillIntervalMillis;

        double tokensToAdd = intervals * refillTokens;

        tokens = Math.min(capacity, tokens + tokensToAdd);

        lastRefillTimestamp += intervals * refillIntervalMillis;
    }
}