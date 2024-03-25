package com.tonggn.techub.application;

import com.tonggn.techub.domain.Feed;
import com.tonggn.techub.domain.Publisher;

public record FeedRequest(
    String title,
    String url,
    String description,
    String thumbnail
) {

  public Feed toEntity(final Publisher publisher) {
    return new Feed(publisher, title, url, description, thumbnail);
  }
}
