package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class SendAccessTokenObject {

    @SerializedName("success") private boolean success;
    @SerializedName("message") private String message;
    @SerializedName("api_key") private String apiKey;
    @SerializedName("user_id") private String userId;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }


    public String getApiKey() {
	return apiKey;
    }

    public void setApiKey(String apiKey) {
	this.apiKey = apiKey;
    }


    public SendAccessTokenObject(boolean success) {
	this.success = success;
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
