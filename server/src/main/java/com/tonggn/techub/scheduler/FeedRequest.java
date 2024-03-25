package com.tonggn.techub.scheduler;

public record FeedRequest(
    String title,
    String url,
    String description,
    String thumbnail
) {

}
