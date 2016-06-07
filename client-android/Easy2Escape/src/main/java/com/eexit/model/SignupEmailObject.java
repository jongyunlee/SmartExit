package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class SignupEmailObject {

    @SerializedName("success") private boolean success;
    @SerializedName("message") private String message;
    @SerializedName("username") private String username;
    @SerializedName("user_id") private String userId;
    @SerializedName("api_key") private String apiKey;
    @SerializedName("profile_image") private String profileImage;

    public String getProfileImage() {
	return profileImage;
    }

    public void setProfileImage(String profileImage) {
	this.profileImage = profileImage;
    }


    public String getApiKey() {
	return apiKey;
    }

    public void setApiKey(String apiKey) {
	this.apiKey = apiKey;
    }


    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }


    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }


    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }


    public boolean getSuccess() {
	return success;
    }

    public void setSuccess(boolean success) {
	this.success = success;
    }
}
