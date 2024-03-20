package com.tonggn.techub.crawler.parser;

public record ParsedFeed(
    String title,
    String url,
    String description,
    String thumbnailUrl
) {

}