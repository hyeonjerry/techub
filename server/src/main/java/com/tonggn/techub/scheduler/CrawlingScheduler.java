package com.tonggn.techub.scheduler;

import com.tonggn.techub.application.SchedulerService;
import com.tonggn.techub.crawler.Crawler;
import com.tonggn.techub.crawler.parser.ParsedFeed;
import com.tonggn.techub.domain.Feed;
import com.tonggn.techub.domain.Publisher;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

  private final SchedulerService schedulerService;

  // cron = "초 분 시 일 월 요일"
  @Scheduled(cron = "0 0 * * * *")
  public void updateFeeds() {
    final List<Publisher> publishers = schedulerService.findAllPublishers();

    for (final Publisher publisher : publishers) {
      final List<Feed> rssFeeds = fetchNewRssFeeds(publisher);

      final List<Feed> openGraphFeeds = fetchOpenGraphFeeds(publisher, rssFeeds);

      final List<Feed> mergedFeeds = mergeFeeds(rssFeeds, openGraphFeeds);

      schedulerService.saveAllFeeds(mergedFeeds);
    }
  }

  private List<Feed> fetchNewRssFeeds(final Publisher publisher) {
    final List<Feed> feeds = Crawler.crawlRss(publisher.getRssUrl()).stream()
        .map(feed -> mapToFeed(publisher, feed))
        .toList();

    return schedulerService.filterNewFeeds(feeds);
  }

  private List<Feed> fetchOpenGraphFeeds(final Publisher publisher, final List<Feed> rssFeeds) {
    return rssFeeds.stream()
        .map(feed -> Crawler.crawlFeed(feed.getUrl()))
        .map(feed -> mapToFeed(publisher, feed))
        .toList();
  }

  private List<Feed> mergeFeeds(final List<Feed> rssFeeds, final List<Feed> openGraphFeeds) {
    return IntStream.range(0, rssFeeds.size())
        .mapToObj(i -> {
          final Feed rssFeed = rssFeeds.get(i);
          final Feed openGraphFeed = openGraphFeeds.get(i);
          return rssFeed.mergeOpenGraphFeed(openGraphFeed);
        })
        .toList();
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
