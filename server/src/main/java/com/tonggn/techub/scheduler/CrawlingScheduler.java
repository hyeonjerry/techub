package com.tonggn.techub.scheduler;

import com.tonggn.techub.application.FeedRequest;
import com.tonggn.techub.application.FeedService;
import com.tonggn.techub.application.PublisherResponse;
import com.tonggn.techub.application.PublisherService;
import com.tonggn.techub.crawler.Crawler;
import com.tonggn.techub.crawler.parser.ParsedFeed;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

  private final PublisherService publisherService;
  private final FeedService feedService;

  // cron = "초 분 시 일 월 요일"
  @Scheduled(cron = "0 0 * * * *")
  public void updateFeeds() {
    final List<PublisherResponse> publishers = publisherService.findAll();

    for (final PublisherResponse publisher : publishers) {
      final Long publisherId = publisher.id();

      final List<ParsedFeed> newRssFeeds = Crawler.crawlRss(publisher.rssUrl())
          .stream()
          .filter(feed -> feedService.isNewFeed(publisherId, feed.url()))
          .toList();

      final List<ParsedFeed> newFeeds = newRssFeeds.stream()
          .map(feed -> Crawler.crawlFeed(feed.url()))
          .toList();

      final List<FeedRequest> saveRequests = newFeeds.stream()
          .map(this::mapToFeedRequest)
          .toList();

      feedService.saveAll(publisherId, saveRequests);
    }
  }

  private FeedRequest mapToFeedRequest(final ParsedFeed feed) {
    return new FeedRequest(feed.title(), feed.url(), feed.description(), feed.thumbnailUrl());
  }
}
