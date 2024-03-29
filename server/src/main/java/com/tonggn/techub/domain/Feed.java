package com.tonggn.techub.domain;

import com.tonggn.techub.base.BaseDatetimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseDatetimeEntity {

  private static final int DESC_LIMIT_LENGTH = 255;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Publisher publisher;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String url;

  private String description;

  private String thumbnailUrl;

  public Feed(
      final Publisher publisher,
      final String title,
      final String url,
      final String description,
      final String thumbnailUrl
  ) {
    this.publisher = publisher;
    this.title = title;
    this.url = decodeUrl(url);
    this.description = trimDescription(description);
    this.thumbnailUrl = decodeUrl(thumbnailUrl);
  }

  private static String decodeUrl(final String url) {
    if (url == null) {
      return null;
    }
    return URLDecoder.decode(url, StandardCharsets.UTF_8);
  }

  private static String trimDescription(final String desc) {
    if (desc == null) {
      return null;
    }
    return desc.length() > DESC_LIMIT_LENGTH ? desc.substring(0, DESC_LIMIT_LENGTH) : desc;
  }

  public Feed mergeOpenGraphFeed(final Feed feed) {
    return new Feed(
        this.publisher,
        this.title,
        this.url,
        getLonger(this.description, feed.description),
        feed.thumbnailUrl
    );
  }

  private String getLonger(final String a, final String b) {
    if (a == null) {
      return b;
    }
    if (b == null) {
      return a;
    }
    return a.length() > b.length() ? a : b;
  }
}
