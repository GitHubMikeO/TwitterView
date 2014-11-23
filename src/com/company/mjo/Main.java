package com.company.mjo;


import com.company.mjo.Controller.ITwitterController;
import com.company.mjo.Model.TwitterFactory;

/**
 * @author mjo
 * @since 10/2014
 * <p>
 * Project Objectives:
 * <ul>
 *   <li>Demonstrate understanding of design patterns, libraries and frameworks.</li>
 * </ul>
 * <p>
 * Project Frameworks:
 * <ul>
 *   <li>Java, Swing, Twitter RESTful API, OAuth, JSON, logger, junit, java docs, Google Simple JSON lib(json-simple-1.1.1.jar)</li>
 * </ul>
 * <p>
 * Design Patterns:
 * <ul>
 *   <li>MVC, Factories, Singleton</li>
 * </ul>
 */
public class Main
{
    public static void main(String[] args)
    {
      ITwitterController twitterController = TwitterFactory.buildTwitter();
      twitterController.showView(true);

     //TwitterFactory.destroyTwitter();
    }
}

