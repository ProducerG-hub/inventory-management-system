package com.inventory_management.security.ratelimit;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, TokenBucket> buckets =
            new ConcurrentHashMap<>();

    //constructor for testing purposes
    public RateLimitService(RateLimitConfig testConfig) {
        
    }

    public boolean isAllowed(
            String clientIp,
            RateLimitPolicy policy
    ) {

        String bucketKey =
                clientIp + ":" + policy.name();

        TokenBucket bucket = buckets.computeIfAbsent(
                bucketKey,
                key -> new TokenBucket(
                        policy.capacity(),
                        policy.refillTokens(),
                        policy.refillIntervalMillis()
                )
        );

        return bucket.tryConsume();
    }

    public long getRetryAfterSeconds(
            String clientIp,
            RateLimitPolicy policy
    ) {

        String bucketKey =
                clientIp + ":" + policy.name();

        TokenBucket bucket = buckets.get(bucketKey);

        if (bucket == null) {
            return 0;
        }

        return bucket.getRetryAfterSeconds();
    }
}