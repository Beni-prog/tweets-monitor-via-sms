package com.example.HotelReviewsMonitoringClient.twilio;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TwilioClient {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2010-04-01/Accounts/{AccountSid}/Messages.json")
    void sendSMSMessage(@Param("AccountSid") String accountSid,
                        @Param("To") String to,
                        @Param("From") String from,
                        @Param("Body") String body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2010-04-01/Accounts/{AccountSid}/Calls.json")
    void sendVoiceMessage(@Param("AccountSid") String accountSid,
                          @Param("To") String to,
                          @Param("From") String from,
                          @Param("Twiml") String twiml);
}
