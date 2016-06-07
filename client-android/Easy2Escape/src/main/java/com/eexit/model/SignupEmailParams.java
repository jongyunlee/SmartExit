package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class SignupEmailParams {

    @SerializedName("username") private String username;
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;
    @SerializedName("recommender") private String recommender;


    public SignupEmailParams(String username,
			     String email,
			     String password,
			     String recommender) {
	this.username = username;
	this.email = email;
	this.password = password;
	this.recommender = recommender;
    }

    public String getRecommender() {
	return recommender;
    }

    public void setRecommender(String recommender) {
	this.recommender = recommender;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }
}
