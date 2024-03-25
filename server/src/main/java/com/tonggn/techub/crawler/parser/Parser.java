package com.tonggn.techub.crawler.parser;

import java.util.List;
import org.jsoup.nodes.Document;

public class Parser {

  private Parser() {
  }

  public static List<ParsedFeed> parseRss(final Document document) {
    final RssType type = RssType.from(document);
    final RssParser parser = type.getParser();
    return parser.parse(document);
  }

  public static ParsedFeed parseFeed(final Document document) {
    return FeedParser.parse(document);
  }
}
