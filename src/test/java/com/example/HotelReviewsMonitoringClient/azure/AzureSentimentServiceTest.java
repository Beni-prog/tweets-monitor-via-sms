package com.example.HotelReviewsMonitoringClient.azure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AzureSentimentServiceTest {

    @Autowired
    private AzureSentimentService azureSentimentService;

    @Test
    public void testPositiveSentiment() throws IOException, InterruptedException {
        SentimentAnalysis sentimentAnalysis = this.azureSentimentService.requestSentimentAnalysis("I love the Landon Hotel!", "en");
        assertNotNull(sentimentAnalysis);
        assertEquals("positive", sentimentAnalysis.getSentiment());
    }

    @Test
    public void testNegativeSentiment() throws IOException, InterruptedException {
        SentimentAnalysis sentimentAnalysis = this.azureSentimentService.requestSentimentAnalysis("Landon Hotel is horrible!", "en");
        assertNotNull(sentimentAnalysis);
        assertEquals("negative", sentimentAnalysis.getSentiment());
    }
}
