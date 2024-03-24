package com.tonggn.techub.crawler;

import com.tonggn.techub.crawler.parser.ParsedFeed;
import com.tonggn.techub.crawler.parser.Parser;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler {

  public static List<ParsedFeed> crawlRss(final String url) {
    final Document response = request(url);
    return Parser.parseRss(response);
  }

  private static Document request(final String url) {
    try {
      return Jsoup.connect(url)
          .get();
    } catch (final IOException e) {
      throw new RuntimeException("Failed to request the URL: " + url, e);
    }
  }
}
