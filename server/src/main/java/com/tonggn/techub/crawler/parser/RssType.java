package com.tonggn.techub.crawler.parser;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;

@RequiredArgsConstructor
enum RssType {
  RDF("rdf:RDF", new RdfRssParser()),
  RSS("rss", new RssRssParser()),
  ATOM("feed", new AtomRssParser());

  private static final int ROOT_TAG_INDEX = 0;

  private final String rootTagName;
  @Getter
  private final RssParser parser;

  public static RssType from(final Document document) {
    final String rootTagName = document.child(ROOT_TAG_INDEX).tagName();
    return Arrays.stream(values())
        .filter(type -> type.isSame(rootTagName))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unsupported RSS type: " + rootTagName));
  }

  public boolean isSame(final String rootTagName) {
    return this.rootTagName.equals(rootTagName);
  }
}
