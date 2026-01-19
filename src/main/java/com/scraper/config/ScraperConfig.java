package com.scraper.config;

public class ScraperConfig {
    private String userAgent;
    private int timeoutMs;
    private int delayBetweenRequestsMs;
    private int maxRetries;
    private boolean followRedirects;

    private ScraperConfig(Builder builder) {
        this.userAgent = builder.userAgent;
        this.timeoutMs = builder.timeoutMs;
        this.delayBetweenRequestsMs = builder.delayBetweenRequestsMs;
        this.maxRetries = builder.maxRetries;
        this.followRedirects = builder.followRedirects;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ScraperConfig defaultConfig() {
        return builder().build();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public int getDelayBetweenRequestsMs() {
        return delayBetweenRequestsMs;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public static class Builder {
        private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
        private int timeoutMs = 10000;
        private int delayBetweenRequestsMs = 1000;
        private int maxRetries = 3;
        private boolean followRedirects = true;

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder timeoutMs(int timeoutMs) {
            this.timeoutMs = timeoutMs;
            return this;
        }

        public Builder delayBetweenRequestsMs(int delayMs) {
            this.delayBetweenRequestsMs = delayMs;
            return this;
        }

        public Builder maxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public Builder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        public ScraperConfig build() {
            return new ScraperConfig(this);
        }
    }
}
