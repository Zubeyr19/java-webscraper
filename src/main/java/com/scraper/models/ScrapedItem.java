package com.scraper.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ScrapedItem {
    private String title;
    private String url;
    private String description;
    private Map<String, String> attributes;
    private LocalDateTime scrapedAt;

    public ScrapedItem() {
        this.attributes = new HashMap<>();
        this.scrapedAt = LocalDateTime.now();
    }

    public ScrapedItem(String title, String url) {
        this();
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    public LocalDateTime getScrapedAt() {
        return scrapedAt;
    }

    @Override
    public String toString() {
        return "ScrapedItem{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
