package com.tonggn.techub.crawler.parser;

import java.util.List;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

abstract class RssParser {

  private static final Map<RssType, RssParser> parsers = Map.of(
      RssType.RDF, new RdfRssParser(),
      RssType.RSS, new RssRssParser(),
      RssType.ATOM, new AtomRssParser()
  );

  public static RssParser from(final RssType type) {
    return parsers.get(type);
  }

  public abstract List<ParsedFeed> parse(Document document);

  protected String selectFirstTextOrEmpty(final Element element, final String selector) {
    final Element item = element.selectFirst(selector);
    return item == null ? "" : item.text();
  }

  protected String selectFirstAttrOrEmpty(
      final Element element,
      final String selector,
      final String attr
  ) {
    final Element item = element.selectFirst(selector);
    return item == null ? "" : item.attr(attr);
  }
}
