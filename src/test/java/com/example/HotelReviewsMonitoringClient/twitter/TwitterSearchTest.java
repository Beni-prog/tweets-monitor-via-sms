package com.example.HotelReviewsMonitoringClient.twitter;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwitterSearchTest {
    @Value(${"TWITTER_BEARER_TOKEN"})
    private final String bearerToken;
    private final static String API_TWITTER_ENDPOINT = "https://api.twitter.com";
    private final static String API_TWITTER_TWEETS_PATH = "/2/tweets/search/recent";

    @Test
    public void restTemplate() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + this.bearerToken);

        String uri = UriComponentsBuilder.fromHttpUrl(API_TWITTER_ENDPOINT + API_TWITTER_TWEETS_PATH)
                .queryParam("query", "Linkedin Learning")
                .build()
                .toUriString();

        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void WebClient() throws InterruptedException {
        WebClient webClient = WebClient.create(API_TWITTER_ENDPOINT);

        Mono<ResponseEntity<String>> mono = webClient.get()
                .uri(API_TWITTER_TWEETS_PATH + "?query={query}", "Linkedin Learning")
                .header("Authorization", "Bearer " + this.bearerToken)
                .retrieve()
                .toEntity(String.class);

        mono.subscribe(response -> {
            System.out.println(response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        });
        System.out.println("This will be executed first, due to asynchronous call.");
        Thread.sleep(40000);
    }
}
