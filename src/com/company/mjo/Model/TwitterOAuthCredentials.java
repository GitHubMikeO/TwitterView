package com.company.mjo.Model;

/**
 * Created by mjo on 10/17/2014.
 *
 * Twitter Authentication ConsumerKey and ConsumerSecret. Retrieve this information by signing up for Twitter
 * developer account at https://dev.twitter.com/.
 */
public class TwitterOAuthCredentials
{
  String oAuthConsumerKey = "";
  String oAuthConsumerSecret = "";

  public String getOAuthConsumerKey()
  {
    return oAuthConsumerKey;
  }

  /**
   * @param consumerKey Twitter consumer key
   */
  public void setOAuthConsumerKey(final String consumerKey)
  {
    oAuthConsumerKey = consumerKey;
  }

  public String getOAuthConsumerSecret()
  {
    return oAuthConsumerSecret;
  }

  /**
   * @param consumerSecret Twitter consumer secret
   */
  public void setOAuthConsumerSecret(final String consumerSecret)
  {
    oAuthConsumerSecret = consumerSecret;
  }

  @Override
  public String toString()
  {
    return "TwitterOAuthCredentials{" + "oAuthConsumerKey='" + oAuthConsumerKey + '\'' + ", oAuthConsumerSecret='" + oAuthConsumerSecret + '\'' + '}';
  }
}
