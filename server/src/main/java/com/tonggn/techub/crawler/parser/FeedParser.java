package com.tonggn.techub.crawler.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class FeedParser {

  public static ParsedFeed parse(final Document document) {
    final String title = extractOpengraph(document, "og:title");
    final String url = extractOpengraph(document, "og:url");
    final String description = extractOpengraph(document, "og:description");
    final String thumbnailUrl = extractOpengraph(document, "og:image");
    return new ParsedFeed.Builder(title, url)
        .description(description)
        .thumbnailUrl(thumbnailUrl)
        .build();
  }

  private static String extractOpengraph(final Document document, final String property) {
    final Element element = document.selectFirst("meta[property='" + property + "']");
    return element == null ? null : element.attr("content");
  }
}
