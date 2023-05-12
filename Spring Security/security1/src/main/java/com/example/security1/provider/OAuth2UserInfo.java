package com.example.security1.provider;

public interface OAuth2UserInfo {
    public String getProviderId();
    public String getProvider();
    public String getName();
    public String getEmail();
}
