package com.tonggn.techub.crawler.parser;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;

@RequiredArgsConstructor
enum RssType {
  RDF("rdf:RDF"),
  RSS("rss"),
  ATOM("feed");

  public static final int ROOT_TAG_INDEX = 0;
  private final String rootTagName;

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
