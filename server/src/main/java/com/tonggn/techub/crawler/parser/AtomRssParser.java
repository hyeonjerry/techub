package com.tonggn.techub.crawler.parser;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class AtomRssParser extends RssParser {

  private static final String TITLE_SELECTOR = "title";
  private static final String URL_SELECTOR = "link";
  private static final String URL_ATTR = "href";

  @Override
  public List<ParsedFeed> parse(final Document document) {
    return document.select("feed > entry").stream()
        .map(this::mapToResponse)
        .toList();
  }

  private ParsedFeed mapToResponse(final Element item) {
    final String title = selectFirstTextOrEmpty(item, TITLE_SELECTOR);
    final String url = selectFirstAttrOrEmpty(item, URL_SELECTOR, URL_ATTR);
    return new ParsedFeed.Builder(title, url)
        .build();
  }
}
