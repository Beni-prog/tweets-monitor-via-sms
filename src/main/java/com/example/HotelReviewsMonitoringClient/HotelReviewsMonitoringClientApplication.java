package com.example.HotelReviewsMonitoringClient;

import com.example.HotelReviewsMonitoringClient.azure.AzureSentimentService;
import com.example.HotelReviewsMonitoringClient.azure.SentimentAnalysis;
import com.example.HotelReviewsMonitoringClient.twilio.TwilioClient;
import com.example.HotelReviewsMonitoringClient.twitter.StreamResponse;
import com.example.HotelReviewsMonitoringClient.twitter.TwitterStreamingService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile("!test")
@SpringBootApplication
public class HotelReviewsMonitoringClientApplication implements CommandLineRunner {

    private static final String twilioSid = "ACe0da70850a27c1d2bc50dd11291450d6";
    private static final String twilioAuthToken = "38729aba1e5b57146bb81d7918a331bd";
    private static final String toNumber = "+40757768609";
    public static final String FROM_NUMBER = "+16812023405";
    public static final String TWILIO_API_DOMAIN = "https://api.twilio.com";

    @Autowired
    private TwitterStreamingService twitterStreamingService;

    @Autowired
    private AzureSentimentService azureSentimentService;

    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public TwilioClient client() {
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);

        return Feign.builder()
                .requestInterceptor(interceptor)
                .decoder(new JacksonDecoder())
                .encoder(new FormEncoder())
                .target(TwilioClient.class, TWILIO_API_DOMAIN);
    }

    public static void main(String[] args) {
        SpringApplication.run(HotelReviewsMonitoringClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        this.twitterStreamingService.stream().subscribe(tweet -> {
            System.out.println("The tweet is: " + tweet);

            try {
                //we have to deserialize the tweet to an object which has the same structure
                StreamResponse response = this.mapper().readValue(tweet, StreamResponse.class);

                SentimentAnalysis sentimentAnalysis = this.azureSentimentService.requestSentimentAnalysis(response.getData().getText(), "en");

                this.client().sendSMSMessage(twilioSid, toNumber, FROM_NUMBER, "A new review has been posted on Twitter, which is: " + sentimentAnalysis.getSentiment());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
