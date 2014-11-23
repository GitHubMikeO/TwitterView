package com.company.mjo.Model;

/**
 * Twitter Bearer Token Domain
 */
public class TwitterBearerToken
{
    private String tokenType;
    private String accessToken;

    public String getTokenType()
    {
        return tokenType;
    }

    public void setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }
}
