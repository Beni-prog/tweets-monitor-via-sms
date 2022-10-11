package com.example.HotelReviewsMonitoringClient.twitter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@SpringBootTest
public class TwitterStreamTest {
    private final String bearerToken = "AAAAAAAAAAAAAAAAAAAAAOI6hwEAAAAA9yQQmpF8%2BE88nuJDkxCtD67w45w%3DNzYvwSEFNapxCX4eJpyYp78308qSPaFPcY5E1yjoMErUlvwgdT";
    private final static String API_TWITTER_ENDPOINT = "https://api.twitter.com";
    private final static String API_TWITTER_STREAM_RULES_PATH = "/2/tweets/search/stream/rules";
    private final static String API_TWITTER_STREAM_PATH = "/2/tweets/search/stream";

    @Autowired
    WebClient.Builder builder;

    @Test
    public void webClient() throws IOException {
        WebClient client = builder
                .baseUrl(API_TWITTER_ENDPOINT)
                .defaultHeaders(headers -> headers.setBearerAuth(bearerToken))
                .build();

        StreamRuleRequest ruleRequest = new StreamRuleRequest();
        ruleRequest.addRule("Linkedin", "Linkedin tag");

        //in this call we establish the rules for our client
        client.post()
                .uri(API_TWITTER_STREAM_RULES_PATH)
                //web client serializes object to json automatically
                .bodyValue(ruleRequest)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> {
                    //in this call we start receiving tweets from the stream
                    client.get()
                            .uri(API_TWITTER_STREAM_PATH)
                            .retrieve()
                            //used to emit multiple values (opposite to mono)
                            .bodyToFlux(String.class)
                            .filter(body -> !body.isBlank())
                            .subscribe(json -> {
                                System.out.println(json);
                            });
                });
        System.in.read();
    }
}
