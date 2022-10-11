package com.example.HotelReviewsMonitoringClient.twilio;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import org.junit.jupiter.api.Test;

public class TwilioClientTest {
    private static final String twilioSid = "ACe0da70850a27c1d2bc50dd11291450d6";
    private static final String twilioAuthToken = "38729aba1e5b57146bb81d7918a331bd";
    private static final String toNumber = "+40757768609";
    public static final String FROM_NUMBER = "+16812023405";
    public static final String TWILIO_API_DOMAIN = "https://api.twilio.com";

    @Test
    public void twilioSendVoiceMessage() {
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);

        TwilioClient client = Feign.builder()
                .requestInterceptor(interceptor)
                .encoder(new FormEncoder())
                .target(TwilioClient.class, TWILIO_API_DOMAIN);

        client.sendVoiceMessage(twilioSid, toNumber, FROM_NUMBER, "<Response><Say>Ahoy there$EXCLAMATION_MARK</Say></Response>");
    }

    @Test
    public void testSMSMessage() {
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);

        TwilioClient client = Feign.builder()
                .requestInterceptor(interceptor)
                .decoder(new JacksonDecoder())
                .encoder(new FormEncoder())
                .target(TwilioClient.class, TWILIO_API_DOMAIN);

        client.sendSMSMessage(twilioSid, toNumber, FROM_NUMBER, "Hello there, Benji!");
    }
}
