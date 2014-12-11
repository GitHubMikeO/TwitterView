package com.company.mjo.View;

import com.company.mjo.Controller.ITwitterController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Created by mjo on 10/17/2014.
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

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize((int)(screenSize.getWidth()/2), (int)(screenSize.getHeight()/2));
    frame.setTitle("TwitterView"); //TODO: add to ResourceBundle

    validateButton.addActionListener(e ->
    {
      retrieveButton.setEnabled(false);

      SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>()
      {
        @Override
        public Boolean doInBackground()
        {
          Boolean successful = false;
          try
          {
            controller.validateCredentials(consumerKeyTextField.getText(), consumerSecretTextField.getText());
            publish("Request Completed");  //TODO: add to ResourceBundle
            successful = true;
          }
          catch (Exception e)
          {
            e.printStackTrace();  //TODO: add Apache log4j
            publish(e.getMessage());
          }
          return successful;
        }
        @Override
        public void done()
        {
          retrieveButton.setEnabled(true);
        }
        @Override
        public void process(List<String> messages)
        {
          for(String message : messages)
            appendStatus(message);
        }
      };
      worker.execute();
    });

    retrieveButton.addActionListener(e ->
    {
      validateButton.setEnabled(false);

      SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>()
      {
        @Override
        public Boolean doInBackground()
        {
          Boolean successful = false;
          try
          {
            controller.displayTimelineTweet(screenNameTextField.getText());
            publish("Request Completed"); //TODO: add to ResourceBundle
            successful = true;
          }
          catch (Exception e)
          {
            e.printStackTrace();  //TODO: add Apache log4j
            publish(e.getMessage());
          }
          return successful;
        }
        @Override
        public void done()
        {
          validateButton.setEnabled(true);
        }
        @Override
        public void process(List<String> messages)
        {
          for(String message : messages)
            appendStatus(message);
        }
      };
      worker.execute();
    });

    clearButton.addActionListener(e -> clearStatus());
  }

  public void showForm(boolean show)
  {
    frame.setVisible(show);
  }

  @Override
  public void appendStatus(String results)
  {
    /**
     * When this action is called from a non EDT Thread,
     * then it isn't going to execute on the EDT and it will potentially block the EDT.
     */
    if (!SwingUtilities.isEventDispatchThread())
      SwingUtilities.invokeLater(() -> statusTextArea.append(results + System.getProperty("line.separator")));
    else
      statusTextArea.append(results + System.getProperty("line.separator"));
  }

  @Override
  public void clearStatus()
  {
    /**
     * When this action is called from a non EDT Thread,
     * then it isn't going to execute on the EDT and it will potentially block the EDT.
     */
    if (!SwingUtilities.isEventDispatchThread())
      SwingUtilities.invokeLater(() -> statusTextArea.setText(null));
    else
      statusTextArea.setText(null);
  }

  @Override
  public void propertyChange(final PropertyChangeEvent evt)
  {
    System.out.print("TwitterView:propertyChange Fired: " + evt.toString());  //add Apache log4j

    if (evt.getPropertyName().startsWith("TwitterOAuthCredentials"))
      appendStatus(evt.toString());
  }
}
