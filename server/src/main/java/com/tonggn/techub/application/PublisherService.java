package com.tonggn.techub.application;

import com.tonggn.techub.domain.Publisher;
import com.tonggn.techub.domain.PublisherRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PublisherService {

  private final PublisherRepository publisherRepository;

  public List<PublisherResponse> findAll() {
    final List<Publisher> publishers = publisherRepository.findAll();
    return publishers.stream()
        .map(PublisherResponse::from)
        .toList();
  }
}
