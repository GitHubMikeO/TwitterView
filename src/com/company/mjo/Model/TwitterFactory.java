package com.company.mjo.Model;

import com.company.mjo.Controller.ITwitterController;
import com.company.mjo.Controller.TwitterController;
import com.company.mjo.View.ITwitterView;
import com.company.mjo.View.TwitterView;
import java.beans.PropertyChangeListener;

/**
 * Created by mjo on 10/17/2014.
 *
 * A factory is an object for creating other objects – formally a factory is simply an object that returns an object
 * from some method call, which is assumed to be "new". More broadly, a subroutine that returns a "new" object may be
 * referred to as a "factory", as in factory method or factory function. This is a basic concept in OOP, and forms the
 * basis for a number of related software design patterns.
 */
public class TwitterFactory
{
  public static ITwitterController buildTwitter()
  {
    ITwitterModel twitterModel = TwitterModel.getSingletonInstance();
    ITwitterView view = TwitterView.getSingletonInstance();

    ITwitterController controller = TwitterController.getSingletonInstance();
    controller.setModel(twitterModel);
    controller.setView(view);

    twitterModel.addPropertyChangeListener((PropertyChangeListener) view);
    twitterModel.addPropertyChangeListener((PropertyChangeListener) controller);

    twitterModel.setController(controller);
    view.setController(controller);

    return controller;
  }

  public static void destroyTwitter()
  {
    ITwitterModel twitterModel = TwitterModel.getSingletonInstance();
    ITwitterController controller = TwitterController.getSingletonInstance();
    ITwitterView view = TwitterView.getSingletonInstance();

    twitterModel.removePropertyChangeListener((PropertyChangeListener) view);
    twitterModel.removePropertyChangeListener((PropertyChangeListener) controller);
  }
}
