# Java Web Scraper

A component-based web scraper built with Java and Jsoup.

## Features

- **HttpClient** - Handles HTTP requests with retry logic and rate limiting
- **HtmlParser** - Parses HTML using CSS selectors
- **DataExporter** - Exports data to JSON, CSV, or console

## Requirements

- Java 17+
- Maven 3.6+

## Quick Start

```bash
# Build
mvn clean package

# Run
java -jar target/java-webscraper-1.0.0.jar
```

## Project Structure

```
src/main/java/com/scraper/
├── Main.java              # Entry point
├── components/
│   ├── HttpClient.java    # HTTP fetching
│   ├── HtmlParser.java    # HTML parsing
│   └── DataExporter.java  # Data export
├── models/
│   └── ScrapedItem.java   # Data model
└── config/
    └── ScraperConfig.java # Configuration
```

## Usage Example

```java
ScraperConfig config = ScraperConfig.builder()
    .timeoutMs(15000)
    .delayBetweenRequestsMs(1500)
    .build();

HttpClient httpClient = new HttpClient(config);
HtmlParser parser = new HtmlParser();
DataExporter exporter = new DataExporter();

Optional<Document> doc = httpClient.fetch("https://example.com");
List<ScrapedItem> items = parser.parseWithSelectors(
    doc.get(),
    ".item",        // container
    "h2 a",         // title
    "h2 a",         // link
    ".description"  // description
);

exporter.toCsv(items, "output/data.csv");
```

## License

MIT
