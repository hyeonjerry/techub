package com.tonggn.techub.scheduler;

import com.tonggn.techub.application.SchedulerService;
import com.tonggn.techub.crawler.Crawler;
import com.tonggn.techub.crawler.parser.ParsedFeed;
import com.tonggn.techub.domain.Feed;
import com.tonggn.techub.domain.Publisher;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CrawlingScheduler {

  private final SchedulerService schedulerService;

  // cron = "초 분 시 일 월 요일"
  @Scheduled(cron = "0 0 * * * *")
  public void updateFeeds() {
    final List<Publisher> publishers = schedulerService.findAllPublishers();
    publishers.forEach(this::updateFeed);
  }

  private void updateFeed(final Publisher publisher) {
    try {
      final List<Feed> newRssFeeds = fetchNewRssFeeds(publisher);

      final List<Feed> feeds = newRssFeeds.stream()
          .map(this::fetchAndMergeOpengraph)
          .filter(Objects::nonNull)
          .toList();

      schedulerService.saveAllFeeds(feeds);
    } catch (final Exception e) {
      log.error("Failed to update feed for {}", publisher.getName(), e);
    }
  }

  private List<Feed> fetchNewRssFeeds(final Publisher publisher) {
    final List<Feed> feeds = Crawler.crawlRss(publisher.getRssUrl()).stream()
        .map(feed -> mapToFeed(publisher, feed))
        .toList();

    return schedulerService.filterNewFeeds(feeds);
  }

  private Feed fetchAndMergeOpengraph(final Feed feed) {
    try {
      final ParsedFeed parsedFeed = Crawler.crawlFeed(feed.getUrl());
      final Feed openGraphFeed = mapToFeed(feed.getPublisher(), parsedFeed);
      return feed.mergeOpenGraphFeed(openGraphFeed);
    } catch (final Exception e) {
      log.error("Failed to fetch OpenGraph data from {}", feed.getUrl(), e);
      return null;
    }
  }

  private Feed mapToFeed(final Publisher publisher, final ParsedFeed feed) {
    return new Feed(
        publisher,
        feed.title(),
        feed.url(),
        feed.description(),
        feed.thumbnailUrl()
    );
  }
}
