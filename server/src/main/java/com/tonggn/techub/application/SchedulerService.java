package com.tonggn.techub.application;

import com.tonggn.techub.domain.Feed;
import com.tonggn.techub.domain.FeedRepository;
import com.tonggn.techub.domain.Publisher;
import com.tonggn.techub.domain.PublisherRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulerService {

  private final FeedRepository feedRepository;
  private final PublisherRepository publisherRepository;

  public List<Publisher> findAllPublishers() {
    return publisherRepository.findAll();
  }

  public List<Feed> filterNewFeeds(final List<Feed> feeds) {
    return feeds.stream()
        .filter(feed -> !feedRepository.existsByPublisherAndUrl(feed.getPublisher(), feed.getUrl()))
        .toList();
  }

  @Transactional
  public void saveAllFeeds(final List<Feed> feeds) {
    feedRepository.saveAll(feeds);
  }
}
