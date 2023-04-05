# tweets-monitor-via-sms
With this project I got a better understanding regarding the REST API in Java. I used three APIs: Azure Cognitive Services API, the Twitter API and the Twilio API.

1. With the help of Twitter API I managed to get all the tweets about a certain subject.
2. With the help of Azure Cognitive Services API I managed to send the tweets to be sentimentally analyzed: a tweet can be positive, negative or neutral, just like a review.
3. With the help of Twilio API, I managed to send the response reveived from Azure API to my phone, via sms, so that I can be informed what kind of review a tweet gave,
each time a tweet was posted about a certain subject on Twitter.
