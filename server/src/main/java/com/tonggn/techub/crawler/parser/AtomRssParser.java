package com.tonggn.techub.crawler.parser;

import java.util.List;
import java.util.function.Function;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class AtomRssParser extends RssParser {

  private static final String TITLE_SELECTOR = "title";
  private static final String URL_SELECTOR = "link";
  private static final String URL_ATTR = "href";
  private static final String DESCRIPTION_SELECTOR = "summary";
  private static final String THUMBNAIL_URL_SELECTOR = "image";

  @Override
  public List<ParsedFeed> parse(final Document document) {
    return document.select("feed > entry").stream()
        .map(mapToResponse())
        .toList();
  }

  private Function<Element, ParsedFeed> mapToResponse() {
    return item -> new ParsedFeed(
        selectFirstText(item, TITLE_SELECTOR),
        selectFirstAttr(item, URL_SELECTOR, URL_ATTR),
        selectFirstText(item, DESCRIPTION_SELECTOR),
        selectFirstText(item, THUMBNAIL_URL_SELECTOR)
    );
  }
}
