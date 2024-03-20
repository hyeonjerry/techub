package com.tonggn.techub.crawler.parser;

import java.util.List;
import java.util.function.Function;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class RssRssParser extends RssParser {

  private static final String FEED_SELECTOR = "channel > item";
  private static final String TITLE_SELECTOR = "title";
  private static final String URL_SELECTOR = "link";
  private static final String DESCRIPTION_SELECTOR = "description";
  private static final String THUMBNAIL_URL_SELECTOR = "image";

  @Override
  public List<ParsedFeed> parse(final Document document) {
    return document.select(FEED_SELECTOR).stream()
        .map(mapToResponse())
        .toList();
  }

  private Function<Element, ParsedFeed> mapToResponse() {
    return item -> new ParsedFeed(
        selectFirstText(item, TITLE_SELECTOR),
        selectFirstText(item, URL_SELECTOR),
        selectFirstText(item, DESCRIPTION_SELECTOR),
        selectFirstText(item, THUMBNAIL_URL_SELECTOR)
    );
  }
}
