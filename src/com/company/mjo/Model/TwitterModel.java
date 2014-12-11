package com.company.mjo.Model;

import com.company.mjo.Controller.ITwitterController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import javax.net.ssl.HttpsURLConnection;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;


/**
 * Created by mjo on 10/17/2014.
 *
 * Twitter Business Logic
 */
public class TwitterModel implements ITwitterModel
{
  private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
  private TwitterOAuthCredentials oAuthCredentials = new TwitterOAuthCredentials();
  private static TwitterModel singletonInstance;
  private static TwitterBearerToken twitterBearerToken;

  private ITwitterController controller;

  private TwitterModel()
  {
  }

  public static TwitterModel getSingletonInstance()
  {
    if (singletonInstance == null)
      singletonInstance = new TwitterModel();

    return singletonInstance;
  }

  public void addPropertyChangeListener(PropertyChangeListener l)
  {
    propertyChangeSupport.addPropertyChangeListener(l);
  }

  public void removePropertyChangeListener(PropertyChangeListener l)
  {
    propertyChangeSupport.removePropertyChangeListener(l);
  }

  public void setController(final ITwitterController controller)
  {
    this.controller = controller;
  }

  @Override
  public void validateCredentials(final TwitterOAuthCredentials newTwitterOAuthCredentials) throws IOException
  {
    System.out.printf("Credentials to validated: Key: %s, Secret: %s", newTwitterOAuthCredentials.getOAuthConsumerKey(), newTwitterOAuthCredentials.getOAuthConsumerSecret());
    propertyChangeSupport.firePropertyChange("TwitterOAuthCredentials", oAuthCredentials.toString(), newTwitterOAuthCredentials.toString());
    oAuthCredentials = newTwitterOAuthCredentials;

    requestBearerToken();
  }

  @Override
  public JSONArray getTimelineTweet(final String screenName) throws IOException
  {
    HttpsURLConnection con = null;
    JSONArray jsonArray = null;

    try
    {
      URL endPointUrl = new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=" + screenName + "&count=2");

      //add logger
      controller.appendStatus(endPointUrl.getPath());

      con = (HttpsURLConnection) endPointUrl.openConnection();
      con.setDoOutput(true);
      con.setDoInput(true);
      con.setRequestMethod("GET");
      con.setRequestProperty("Host", "api.twitter.com");
      con.setRequestProperty("Authorization", "Bearer " + twitterBearerToken.getAccessToken());
      con.setUseCaches(false);

      // Parse the JSON response into a JSON mapped object to fetch fields from.
      jsonArray = (JSONArray)JSONValue.parse(readResponse(con));
  }
    finally
    {
      if (con != null)
        con.disconnect();
    }
    return jsonArray;
  }

  private void requestBearerToken() throws IOException
  {
    // Do the OAUTH2 Authenticate
    String oAuthTokenUrl = "https://api.twitter.com/oauth2/token";
    URL tokenURL = new URL(oAuthTokenUrl);

    HttpsURLConnection con = (HttpsURLConnection) tokenURL.openConnection();
    con.setDoOutput(true);
    con.setDoInput(true);
    con.setRequestMethod("POST");
    con.setRequestProperty("Host", "api.twitter.com");
    con.setRequestProperty("Authorization", "Basic " + encodeKeys(oAuthCredentials));
    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    con.setUseCaches(false);

    writeRequest(con, "grant_type=client_credentials");

    // Parse the JSON response into a JSON mapped object to fetch fields from.
   JSONObject obj = (JSONObject) JSONValue.parse(readResponse(con));

    if (obj == null)
      throw new IOException("Bearer token request failed.");  //TODO: add to ResourceBundle
    else
    {
      String tokenType = (String) obj.get("token_type");
      String accessToken = (String) obj.get("access_token");

      if(tokenType.equals("bearer") && (accessToken != null))
      {
        twitterBearerToken = new TwitterBearerToken();
        twitterBearerToken.setTokenType(tokenType);
        twitterBearerToken.setAccessToken(accessToken);

        controller.appendStatus("token_type: " + twitterBearerToken.getTokenType());      //TODO: add to ResourceBundle
        controller.appendStatus("access_token: " + twitterBearerToken.getAccessToken());  //TODO: add to ResourceBundle
      }
    }
    con.disconnect();
  }

  /**
   * Encode credentials according to https://dev.twitter.com/oauth/application-only
   * @param oAuthCredentials TwitterOAuthCredentials
   * @return encoded keys
   */
  private static String encodeKeys(TwitterOAuthCredentials oAuthCredentials)
  {
    try
    {
      String encodedConsumerKey = URLEncoder.encode(oAuthCredentials.getOAuthConsumerKey(), "UTF-8");  //RFC1738 encoding
      String encodedConsumerSecret = URLEncoder.encode(oAuthCredentials.getOAuthConsumerSecret(), "UTF-8"); //RFC1738 encoding

      String concatenatedKeySecret = encodedConsumerKey + ":" + encodedConsumerSecret;
      return Base64.getEncoder().encodeToString(concatenatedKeySecret.getBytes("UTF-8"));
    }
    catch(UnsupportedEncodingException e)
    {
      //change to logger
      e.printStackTrace();
      return "";
    }
  }

  // Writes a request to a connection
  private static void writeRequest(HttpsURLConnection connection, String textBody) throws IOException
  {
      BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
      wr.write(textBody);
      wr.flush();
      wr.close();
  }

  // Reads a response for a given connection and returns it as a string.
  private static String readResponse(HttpsURLConnection connection) throws IOException
  {
    StringBuilder str = new StringBuilder();
    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    String line;
    while((line = br.readLine()) != null)
    {
      str.append(line).append(System.getProperty("line.separator"));
    }
    return str.toString();
  }
}

