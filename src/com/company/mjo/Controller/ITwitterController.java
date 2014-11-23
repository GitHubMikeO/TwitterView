package com.company.mjo.Controller;

import com.company.mjo.Model.ITwitterModel;
import com.company.mjo.View.ITwitterView;

/**
 * Created by mjo on 10/17/2014.
 *
 * Interface used for separation of concerns, maintainability and testability.
 */
public interface ITwitterController
{
  /**
   * Twitter Authentication ConsumerKey and ConsumerSecret used for access to Twitter Restful API. Retrieve this information by signing up for Twitter
   * developer account at https://dev.twitter.com/.
   *
   * @param consumerKey Consumer Key from dev.twitter.com
   * @param consumerSecret Consumer Secret from dev.twitter.com
   */
  public void validateCredentials(String consumerKey, String consumerSecret);

  /**
   * Retrieve tweet from Twitter timeline and display.
   * @param accountName Public Twitter Account, ie. twitterapi
   */
  public void displayTimelineTweet(String accountName);

  /**
   * Appends text in status view.
   * @param status text to display
   */
  public void appendStatus(String status);

  /**
   * Clears text from status view.
   */
  public void clearStatus();

  /**
   * Provides access to ITwitterView interface.
   * @param view ITwitterView
   */
  public void setView(ITwitterView view);

  /**
   * Provides access to ITwitterModel interface.
   * @param model ITwitterModel
   */
  public void setModel(ITwitterModel model);

  /**
   * Show/hide ITwitterView UI
   * @param show UI
   */
  public void showView(boolean show);
}
