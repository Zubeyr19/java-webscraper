package com.scraper.components;

import com.scraper.config.ScraperConfig;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private final ScraperConfig config;

    public HttpClient(ScraperConfig config) {
        this.config = config;
    }

    public Optional<Document> fetch(String url) {
        int attempts = 0;

        while (attempts < config.getMaxRetries()) {
            try {
                logger.info("Fetching URL: {} (attempt {})", url, attempts + 1);

                Connection connection = Jsoup.connect(url)
                        .userAgent(config.getUserAgent())
                        .timeout(config.getTimeoutMs())
                        .followRedirects(config.isFollowRedirects());

                Document doc = connection.get();

                if (config.getDelayBetweenRequestsMs() > 0) {
                    Thread.sleep(config.getDelayBetweenRequestsMs());
                }

                logger.info("Successfully fetched: {}", url);
                return Optional.of(doc);

            } catch (IOException e) {
                attempts++;
                logger.warn("Failed to fetch {} (attempt {}): {}", url, attempts, e.getMessage());

                if (attempts >= config.getMaxRetries()) {
                    logger.error("Max retries reached for URL: {}", url);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread interrupted while waiting between requests");
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    public Optional<String> fetchRaw(String url) {
        return fetch(url).map(Document::html);
    }
}
