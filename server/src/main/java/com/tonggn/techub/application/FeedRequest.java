package com.tonggn.techub.application;

public record FeedRequest(
    String title,
    String url,
    String description,
    String thumbnail
) {

}
