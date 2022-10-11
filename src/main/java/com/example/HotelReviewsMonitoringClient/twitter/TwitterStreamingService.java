package com.example.HotelReviewsMonitoringClient.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class TwitterStreamingService {
    @Value(${"TWITTER_BEARER_TOKEN"})
    private String bearerToken;
    private final static String API_TWITTER_ENDPOINT = "https://api.twitter.com";
    private final static String API_TWITTER_STREAM_PATH = "/2/tweets/search/stream";

    @Autowired
    WebClient.Builder builder;

    public Flux<String> stream() {
        //Connect to the stream and return the flux from method
        WebClient client = builder
                .baseUrl(API_TWITTER_ENDPOINT)
                .defaultHeaders(headers -> headers.setBearerAuth(bearerToken))
                .build();

        return client.get()
                .uri(API_TWITTER_STREAM_PATH)
                .retrieve()
                .bodyToFlux(String.class)
                .filter(tweet -> !tweet.isBlank());
    }
}
