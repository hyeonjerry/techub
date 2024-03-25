package com.tonggn.techub.crawler.parser;

public record ParsedFeed(
    String title,
    String url,
    String description,
    String thumbnailUrl
) {

  public static class Builder {

    private final String title;
    private final String url;
    private String description = "";
    private String thumbnailUrl = "";

    public Builder(final String title, final String url) {
      this.title = title;
      this.url = url;
    }

    public Builder description(final String description) {
      this.description = description;
      return this;
    }

    public Builder thumbnailUrl(final String thumbnailUrl) {
      this.thumbnailUrl = thumbnailUrl;
      return this;
    }

    public ParsedFeed build() {
      return new ParsedFeed(title, url, description, thumbnailUrl);
    }
  }
}
