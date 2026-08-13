package com.inventory_management.security.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RateLimitPolicyResolver {

    private final RateLimitProperties properties;

    public RateLimitPolicyResolver(
        RateLimitProperties properties
    ) {
        this.properties = properties;
    }

    public RateLimitPolicy resolve(HttpServletRequest request) {

        String path = request.getRequestURI();


        if (path.startsWith("/api/auth/")) {
            RateLimitProperties.Policy policy = properties.getAuth();
            return new RateLimitPolicy(
                "AUTH",
                    policy.getCapacity(),
                    policy.getRefillTokens(),
                    policy.getRefillIntervalMillis()
            );
        }

        if (path.startsWith("/api/reports/")) {
            RateLimitProperties.Policy policy = properties.getReports();
            return new RateLimitPolicy(
                "REPORTS",
                policy.getCapacity(),
                policy.getRefillTokens(),
                policy.getRefillIntervalMillis()
            );
        }

        if (path.startsWith("/api/sales/")) {
            RateLimitProperties.Policy policy = properties.getSales();
            return new RateLimitPolicy(
                "SALES",
                    policy.getCapacity(),
                    policy.getRefillTokens(),
                    policy.getRefillIntervalMillis()
            );
        }

        // Default policy for other API endpoints
        RateLimitProperties.Policy policy = properties.getDefaultPolicy();
        return new RateLimitPolicy(
            "DEFAULT",
            policy.getCapacity(),
            policy.getRefillTokens(),
            policy.getRefillIntervalMillis()
        );
    }
}