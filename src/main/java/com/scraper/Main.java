package com.scraper;

import com.scraper.components.DataExporter;
import com.scraper.components.HtmlParser;
import com.scraper.components.HttpClient;
import com.scraper.config.ScraperConfig;
import com.scraper.models.ScrapedItem;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting Web Scraper...");

        // Initialize components
        ScraperConfig config = ScraperConfig.builder()
                .timeoutMs(15000)
                .delayBetweenRequestsMs(1500)
                .maxRetries(3)
                .build();

        HttpClient httpClient = new HttpClient(config);
        HtmlParser parser = new HtmlParser();
        DataExporter exporter = new DataExporter();

        // Example: Scrape Hacker News front page
        String targetUrl = "https://news.ycombinator.com";

        logger.info("Scraping: {}", targetUrl);
        Optional<Document> docOpt = httpClient.fetch(targetUrl);

        if (docOpt.isPresent()) {
            Document doc = docOpt.get();

            // Parse Hacker News articles
            List<ScrapedItem> items = parser.parseWithSelectors(
                    doc,
                    ".athing",           // Container for each article
                    ".titleline > a",    // Title selector
                    ".titleline > a",    // Link selector (same as title)
                    null                 // No description on HN front page
            );

            // Output to console
            exporter.toConsole(items);

            // Export to files
            try {
                exporter.toJson(items, "output/scraped_data.json");
                exporter.toCsv(items, "output/scraped_data.csv");
                logger.info("Data exported successfully!");
            } catch (Exception e) {
                logger.error("Failed to export data: {}", e.getMessage());
            }
        } else {
            logger.error("Failed to fetch the target URL");
        }

        logger.info("Scraper finished.");
    }
}
