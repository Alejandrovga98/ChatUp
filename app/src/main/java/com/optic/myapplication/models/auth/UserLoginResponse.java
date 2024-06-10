package com.optic.myapplication.models.auth;

public class UserLoginResponse {

    private String jwt;
    private String userId;

    public UserLoginResponse(String jwt, String userId) {
        this.jwt = jwt;
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
