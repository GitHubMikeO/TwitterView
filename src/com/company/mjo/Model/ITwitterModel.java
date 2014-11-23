package com.company.mjo.Model;

import com.company.mjo.Controller.ITwitterController;
import org.json.simple.JSONArray;

import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Created by mjo on 10/17/2014.
 *
 * Interface used for separation of concerns, maintainability and testability.
 */
public interface ITwitterModel
{
  /**
   * Validates users Twitter credentials.
   * @param twitterOAuthCredentials @see TwitterOAuthCredentials
   * @throws IOException if problems occur during validation
   */
  public void validateCredentials(TwitterOAuthCredentials twitterOAuthCredentials) throws IOException;

  /**
   * Retrieve tweet from Twitter timeline.
   * @param screenName Public Twitter Account, ie. twitterapi
   */
  public JSONArray getTimelineTweet(final String screenName) throws IOException;


  /**
   * Provides access to ITwitterController interface.
   * @param controller ITwitterController
   */
  public void setController(final ITwitterController controller);

  /**
   * Listen for model changes.
   * @param l PropertyChangeListener
   */
  public void addPropertyChangeListener(PropertyChangeListener l);

  /**
   * Removes model listener.
   * @param l PropertyChangeListener
   */
  public void removePropertyChangeListener(PropertyChangeListener l);
}
