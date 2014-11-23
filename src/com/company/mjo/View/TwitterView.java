package com.company.mjo.View;

import com.company.mjo.Controller.ITwitterController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by mjo on 10/17/2014.
 *
 * User interface for Twitter RESTful API Service.
 */
public class TwitterView implements ITwitterView, PropertyChangeListener
{
  private static TwitterView singletonInstance;
  private JPanel mainPanel;
  private JTextField screenNameTextField;
  private JButton retrieveButton;
  private JButton clearButton;
  private JLabel screenNameLabel;
  private JTextField consumerKeyTextField;
  private JTextField consumerSecretTextField;
  private JButton validateButton;
  private JTextArea statusTextArea;
  private JFrame frame;

  private TwitterView()
  {
  }

  public static TwitterView getSingletonInstance()
  {
    if (singletonInstance == null)
      singletonInstance = new TwitterView();

    return singletonInstance;
  }


  public void setController(ITwitterController controller)
  {
    frame = new JFrame();
    frame.add(mainPanel);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    validateButton.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          controller.validateCredentials(consumerKeyTextField.getText(), consumerSecretTextField.getText());
        }
    });

    retrieveButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        controller.displayTimelineTweet(screenNameTextField.getText());
      }
    });

    clearButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        controller.clearStatus();
      }
    });
  }

  public void showForm(boolean show)
  {
    frame.setVisible(show);
    frame.pack();
  }

  @Override
  public void appendStatus(String results)
  {
    statusTextArea.append(results + System.getProperty("line.separator"));
  }

  @Override
  public void clearStatus()
  {
    statusTextArea.setText(null);
  }

  @Override
  public void propertyChange(final PropertyChangeEvent evt)
  {
    System.out.print("TwitterView:propertyChange Fired: " + evt.toString());
    if (evt.getPropertyName().startsWith("TwitterOAuthCredentials"))
    {
      appendStatus(evt.toString());
    }
  }
}
