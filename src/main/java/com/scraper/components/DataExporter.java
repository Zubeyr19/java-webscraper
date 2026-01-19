package com.scraper.components;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scraper.models.ScrapedItem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataExporter {
    private static final Logger logger = LoggerFactory.getLogger(DataExporter.class);
    private final Gson gson;

    public DataExporter() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public void toJson(List<ScrapedItem> items, String filePath) throws IOException {
        ensureParentDirExists(filePath);

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(items, writer);
            logger.info("Exported {} items to JSON: {}", items.size(), filePath);
        }
    }

    public void toCsv(List<ScrapedItem> items, String filePath) throws IOException {
        ensureParentDirExists(filePath);

        try (FileWriter writer = new FileWriter(filePath);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("Title", "URL", "Description", "Scraped At"))) {

            for (ScrapedItem item : items) {
                printer.printRecord(
                        item.getTitle(),
                        item.getUrl(),
                        item.getDescription(),
                        item.getScrapedAt()
                );
            }

            logger.info("Exported {} items to CSV: {}", items.size(), filePath);
        }
    }

    public String toJsonString(List<ScrapedItem> items) {
        return gson.toJson(items);
    }

    public void toConsole(List<ScrapedItem> items) {
        System.out.println("\n=== Scraped Data ===\n");
        for (int i = 0; i < items.size(); i++) {
            ScrapedItem item = items.get(i);
            System.out.printf("[%d] %s%n", i + 1, item.getTitle());
            System.out.printf("    URL: %s%n", item.getUrl());
            if (item.getDescription() != null && !item.getDescription().isEmpty()) {
                System.out.printf("    Desc: %s%n", truncate(item.getDescription(), 100));
            }
            System.out.println();
        }
        System.out.printf("Total: %d items%n", items.size());
    }

    private void ensureParentDirExists(String filePath) throws IOException {
        Path path = Path.of(filePath);
        Path parent = path.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}
