package com.eexit.model;

import com.google.gson.annotations.SerializedName;

public class SplashResult {

    @SerializedName("success") private boolean success;
    @SerializedName("has_alert_msg") private boolean has_message;
    @SerializedName("message") private String message;
    @SerializedName("api_key") private String session_key;
    @SerializedName("username") private String nickname;
    @SerializedName("user_id") private String user_id;
    @SerializedName("session_success") private boolean session_check_success;

    @SerializedName("alert_msg") private AlertMessage alert_message;

    @SerializedName("profile_image") private String profileImage;
    @SerializedName("cover_image") private String coverImage;
    @SerializedName("country") private String country;
    @SerializedName("title") private String title;
    @SerializedName("flag") private String flag;
    @SerializedName("city") private String city;
    @SerializedName("phone_number") private String phoneNumber;

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }


    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }


    public String getFlag() {
	return flag;
    }

    public void setFlag(String flag) {
	this.flag = flag;
    }


    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }


    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }


    public String getCoverImage() {
	return coverImage;
    }

    public void setCoverImage(String coverImage) {
	this.coverImage = coverImage;
    }


    public String getProfileImage() {
	return profileImage;
    }

    public void setProfileImage(String profileImage) {
	this.profileImage = profileImage;
    }


    public SplashResult(boolean success, String message) {
	this.success = success;
	this.message = message;
    }

    public AlertMessage getAlertMessage() {
	return alert_message;
    }

    public void setAlertMessage(AlertMessage alert_message) {
	this.alert_message = alert_message;
    }

    public String getNickname() {
	return nickname;
    }

    public void setNickname(String nickname) {
	this.nickname = nickname;
    }

    public String getUserId() {
	return user_id;
    }

    public void setUserId(String user_id) {
	this.user_id = user_id;
    }


    public String getSessionKey() {
	return session_key;
    }

    public void setSessionKey(String session_key) {
	this.session_key = session_key;
    }


    public boolean getSessionCheckSuccess() {
	return session_check_success;
    }

    public void setSessionCheckSuccess(boolean session_check_success) {
	this.session_check_success = session_check_success;
    }

    public boolean getHasMessage() {
	return has_message;
    }

    public void setHasMessage(boolean has_message) {
	this.has_message = has_message;
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
