package com.scraper.components;

import com.scraper.models.ScrapedItem;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HtmlParser {
    private static final Logger logger = LoggerFactory.getLogger(HtmlParser.class);

    public List<ScrapedItem> parseWithSelectors(Document doc, String containerSelector,
                                                 String titleSelector, String linkSelector,
                                                 String descriptionSelector) {
        List<ScrapedItem> items = new ArrayList<>();
        Elements containers = doc.select(containerSelector);

        logger.info("Found {} containers matching selector: {}", containers.size(), containerSelector);

        for (Element container : containers) {
            ScrapedItem item = new ScrapedItem();

            Element titleEl = container.selectFirst(titleSelector);
            if (titleEl != null) {
                item.setTitle(titleEl.text().trim());
            }

            Element linkEl = container.selectFirst(linkSelector);
            if (linkEl != null) {
                String href = linkEl.attr("abs:href");
                if (href.isEmpty()) {
                    href = linkEl.attr("href");
                }
                item.setUrl(href);
            }

            if (descriptionSelector != null && !descriptionSelector.isEmpty()) {
                Element descEl = container.selectFirst(descriptionSelector);
                if (descEl != null) {
                    item.setDescription(descEl.text().trim());
                }
            }

            if (item.getTitle() != null || item.getUrl() != null) {
                items.add(item);
            }
        }

        logger.info("Parsed {} items from document", items.size());
        return items;
    }

    public List<ScrapedItem> parseCustom(Document doc, Function<Document, List<ScrapedItem>> extractor) {
        return extractor.apply(doc);
    }

    public List<String> extractLinks(Document doc) {
        List<String> links = new ArrayList<>();
        Elements anchors = doc.select("a[href]");

        for (Element anchor : anchors) {
            String href = anchor.attr("abs:href");
            if (!href.isEmpty() && href.startsWith("http")) {
                links.add(href);
            }
        }

        return links;
    }

    public List<String> extractText(Document doc, String selector) {
        List<String> texts = new ArrayList<>();
        Elements elements = doc.select(selector);

        for (Element el : elements) {
            texts.add(el.text().trim());
        }

        return texts;
    }
}
