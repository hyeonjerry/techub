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
public class FeedService {

  private final FeedRepository feedRepository;
  private final PublisherRepository publisherRepository;

  public boolean isNewFeed(final Long publisherId, final String url) {
    return !feedRepository.existsByPublisherIdAndUrl(publisherId, url);
  }

  @Transactional
  public void saveAll(final Long publisherId, final List<FeedRequest> requests) {
    final Publisher publisher = publisherRepository.findById(publisherId)
        .orElseThrow(() -> new IllegalArgumentException("Publisher not found: " + publisherId));

    final List<Feed> feeds = requests.stream()
        .map(request -> request.toEntity(publisher))
        .toList();

    feedRepository.saveAll(feeds);
  }
}
