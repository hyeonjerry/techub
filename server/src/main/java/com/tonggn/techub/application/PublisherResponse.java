package com.tonggn.techub.application;

import com.tonggn.techub.domain.Publisher;

public record PublisherResponse(
    Long id,
    String name,
    String rssUrl,
    String websiteUrl
) {

  public static PublisherResponse from(final Publisher publisher) {
    return new PublisherResponse(
        publisher.getId(),
        publisher.getName(),
        publisher.getRssUrl(),
        publisher.getWebsiteUrl()
    );
  }
}
