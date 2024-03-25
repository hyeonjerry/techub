package com.tonggn.techub.crawler.parser;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class RdfRssParser extends RssParser {

  private static final String FEED_SELECTOR = "item";
  private static final String TITLE_SELECTOR = "title";
  private static final String URL_SELECTOR = "link";

  @Override
  public List<ParsedFeed> parse(final Document document) {
    return document.select(FEED_SELECTOR).stream()
        .map(this::mapToResponse)
        .toList();
  }

  private ParsedFeed mapToResponse(final Element item) {
    final String title = selectFirstTextOrEmpty(item, TITLE_SELECTOR);
    final String url = selectFirstTextOrEmpty(item, URL_SELECTOR);
    return new ParsedFeed.Builder(title, url)
        .build();
  }
}
