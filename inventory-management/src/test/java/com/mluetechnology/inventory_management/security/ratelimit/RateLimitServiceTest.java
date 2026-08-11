package com.mluetechnology.inventory_management.security.ratelimit;

import org.junit.jupiter.api.Test;

import com.inventory_management.security.ratelimit.RateLimitConfig;
import com.inventory_management.security.ratelimit.RateLimitService;

import static org.junit.jupiter.api.Assertions.*;

class RateLimitServiceTest {

    private RateLimitConfig createTestConfig() {

        RateLimitConfig config = new RateLimitConfig();

        config.setCapacity(10);
        config.setRefillTokens(1);
        config.setRefillIntervalMs(1000);

        return config;
    }

    @Test
    void shouldAllowRequestsWithinLimit() {

        RateLimitService service =
                new RateLimitService(createTestConfig());

        String ip = "::1";

        for (int i = 0; i < 10; i++) {
            assertTrue(service.isAllowed(ip));
        }
    }

    @Test
    void shouldRejectRequestWhenLimitIsExceeded() {

        RateLimitService service =
                new RateLimitService(createTestConfig());

        String ip = "::1";

        for (int i = 0; i < 10; i++) {
            assertTrue(service.isAllowed(ip));
        }

        assertFalse(service.isAllowed(ip));
    }

    @Test
    void shouldMaintainSeparateLimitsForDifferentIps() {

        RateLimitService service =
                new RateLimitService(createTestConfig());

        String ip1 = "::1";
        String ip2 = "192.168.1.20";

        for (int i = 0; i < 10; i++) {
            assertTrue(service.isAllowed(ip1));
        }

        assertFalse(service.isAllowed(ip1));

        assertTrue(service.isAllowed(ip2));
    }
}