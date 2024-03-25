package com.tonggn.techub.crawler.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jsoup.parser.Parser.xmlParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParserTest {

  private static final String rdfPathname = "src/test/resources/rss/rdf.xml";
  private static final String rssPathname = "src/test/resources/rss/rss.xml";
  private static final String atomPathname = "src/test/resources/rss/atom.xml";
  private final List<ParsedFeed> expected = List.of(
      new ParsedFeed("item1", "http://www.example.com/item/1"),
      new ParsedFeed("item2", "http://www.example.com/item/2")
  );

  @Test
  @DisplayName("Rss 2.0 파싱 테스트")
  void RssParseTest() throws IOException {
    // given
    final File file = new File(rssPathname);
    final Document document = Jsoup.parse(file, "UTF-8", "", xmlParser());

    // when
    final List<ParsedFeed> actual = Parser.parseRss(document);

    // then
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  @DisplayName("RDF 파싱 테스트")
  void RdfParseTest() throws IOException {
    // given
    final File file = new File(rdfPathname);
    final Document document = Jsoup.parse(file, "UTF-8", "", xmlParser());

    // when
    final List<ParsedFeed> actual = Parser.parseRss(document);

    // then
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  @DisplayName("Atom 파싱 테스트")
  void AtomParseTest() throws IOException {
    // given
    final File file = new File(atomPathname);
    final Document document = Jsoup.parse(file, "UTF-8", "", xmlParser());

    // when
    final List<ParsedFeed> actual = Parser.parseRss(document);

    // then
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
