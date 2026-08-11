package com.mluetechnology.inventory_management.security.ratelimit;

import com.inventory_management.security.ratelimit.TokenBucket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenBucketTest {

    @Test
    void shouldAllowRequestsUntilBucketIsEmpty() {

        TokenBucket bucket = new TokenBucket(
                3,
                1,
                1000
        );

        assertTrue(bucket.tryConsume());
        assertTrue(bucket.tryConsume());
        assertTrue(bucket.tryConsume());

        assertFalse(bucket.tryConsume());
    }

    @Test
    void shouldRefillTokensAfterInterval() throws InterruptedException {

        TokenBucket bucket = new TokenBucket(
                1,
                1,
                100
        );

        assertTrue(bucket.tryConsume());

        assertFalse(bucket.tryConsume());

        Thread.sleep(150);

        assertTrue(bucket.tryConsume());
    }

    @Test
    void shouldNotExceedBucketCapacity() throws InterruptedException {

        TokenBucket bucket = new TokenBucket(
                3,
                5,
                100
        );

        Thread.sleep(150);

        assertTrue(bucket.tryConsume());
        assertTrue(bucket.tryConsume());
        assertTrue(bucket.tryConsume());

        assertFalse(bucket.tryConsume());
    }
}