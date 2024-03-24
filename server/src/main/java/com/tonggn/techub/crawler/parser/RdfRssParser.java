package com.tonggn.techub.crawler.parser;

import java.util.List;
import java.util.function.Function;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class RdfRssParser extends RssParser {

  private static final String FEED_SELECTOR = "item";
  private static final String TITLE_SELECTOR = "title";
  private static final String URL_SELECTOR = "link";

  @Override
  public List<ParsedFeed> parse(final Document document) {
    return document.select(FEED_SELECTOR).stream()
        .map(mapToResponse())
        .toList();
  }

  private Function<Element, ParsedFeed> mapToResponse() {
    return item -> new ParsedFeed(
        selectFirstTextOrEmpty(item, TITLE_SELECTOR),
        selectFirstTextOrEmpty(item, URL_SELECTOR)
    );
  }
}
