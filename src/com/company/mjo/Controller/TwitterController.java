package com.company.mjo.Controller;


import com.company.mjo.Model.ITwitterModel;
import com.company.mjo.Model.TwitterOAuthCredentials;
import com.company.mjo.View.ITwitterView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Created by mjo on 10/17/2014.
 *
 * MVC Architectural Pattern - Controller
 *
 * Controller can directly communicate with the View, Model and other controllers.
 * The controller can also listen to model changes via PropertyChangeListener interface.
 * Singleton pattern is uses because only 1 object is needed to coordinate actions.
 */
public class TwitterController implements ITwitterController, PropertyChangeListener
{
  private static TwitterController singletonInstance;
  private ITwitterModel model;
  private ITwitterView view;
  private TwitterOAuthCredentials twitterOAuthCredentials;

  private TwitterController()
  {
  }
  public static TwitterController getSingletonInstance()
  {
    if (singletonInstance == null)
      singletonInstance = new TwitterController();

    return singletonInstance;
  }

  public void setModel(final ITwitterModel twitterModel)
  {
    model = twitterModel;
  }

  public void setView(final ITwitterView twitterView)
  {
    view = twitterView;
  }

  public void showView(final boolean show)
  {
    view.showForm(show);
  }

  @Override
  public void validateCredentials(final String consumerKey, final String consumerSecret) throws IOException
  {
    if(twitterOAuthCredentials == null)
      twitterOAuthCredentials = new TwitterOAuthCredentials();

    twitterOAuthCredentials.setOAuthConsumerKey(consumerKey);
    twitterOAuthCredentials.setOAuthConsumerSecret(consumerSecret);

    //simulate long running operation
    for(int i = 1; i<=3; i++)
    {
      appendStatus("Update from non event dispatch thread: " + i);  //TODO: add to ResourceBundle
      try
      {
        Thread.sleep(1000);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    model.validateCredentials(twitterOAuthCredentials);
  }

  @Override
  public void displayTimelineTweet(final String accountName) throws IOException
  {
    JSONArray tweets = model.getTimelineTweet(accountName);
    if (tweets != null)
    {
      for (Object tweet : tweets)
        appendStatus("TWEET: " + ((JSONObject) tweet).get("text").toString());
    }
  }

  @Override
  public void clearStatus()
  {
    if(view != null)
      view.clearStatus();
  }

  @Override
  public void appendStatus(String status)
  {
     if(view != null)
       view.appendStatus(status);
  }

  @Override
  public void propertyChange(final PropertyChangeEvent evt)
  {
    if (view != null && evt.getPropertyName().startsWith("TwitterOAuthCredentials"))
      view.appendStatus("TwitterController: TwitterOAuthCredentials Updated");
  }
}
