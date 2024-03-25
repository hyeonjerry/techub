package com.tonggn.techub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

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

  public Feed(final Publisher publisher, final String title, final String url, final String description,
      final String thumbnailUrl) {
    this.publisher = publisher;
    this.title = title;
    this.url = url;
    this.description = description;
    this.thumbnailUrl = thumbnailUrl;
  }
}
