package com.tonggn.techub.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

  boolean existsByPublisherAndUrl(Publisher publisher, String url);
}
