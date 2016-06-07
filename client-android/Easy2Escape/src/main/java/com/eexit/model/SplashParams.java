package com.eexit.model;

import com.google.gson.annotations.SerializedName;

public class SplashParams {

    @SerializedName("version_code") private int version_code;
    @SerializedName("token") private String token;
    @SerializedName("new_token") private boolean new_token = false;

    public boolean getNewToken() {
	return new_token;
    }

    public void setNewToken(boolean new_token) {
	this.new_token = new_token;
    }


    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }


    public SplashParams(int version_code, String token, boolean new_token) {
    	this.version_code = version_code;
    	this.token = token;
    	this.new_token = new_token;
    }

    public int getVersionCode() {
	return version_code;
    }

    public void setVersionCode(int version_code) {
	this.version_code = version_code;
    }
}
