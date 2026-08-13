package com.inventory_management.security.ratelimit;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.rate-limit")
public class RateLimitProperties {

    private Policy auth;
    private Policy products;
    private Policy sales;
    private Policy reports;
    private Policy defaultPolicy;

    public Policy getAuth() {
        return auth;
    }

    public void setAuth(Policy auth) {
        this.auth = auth;
    }

    public Policy getProducts() {
        return products;
    }

    public void setProducts(Policy products) {
        this.products = products;
    }

    public Policy getSales() {
        return sales;
    }

    public void setSales(Policy sales) {
        this.sales = sales;
    }

    public Policy getReports() {
        return reports;
    }

    public void setReports(Policy reports) {
        this.reports = reports;
    }

    public Policy getDefaultPolicy() {
        return defaultPolicy;
    }

    public void setDefaultPolicy(Policy defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
    }

    public static class Policy {

        private long capacity;
        private long refillTokens;
        private long refillIntervalMillis;

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

        public long getRefillIntervalMillis() {
            return refillIntervalMillis;
        }

        public void setRefillIntervalMillis(
                long refillIntervalMillis
        ) {
            this.refillIntervalMillis = refillIntervalMillis;
        }
    }
}