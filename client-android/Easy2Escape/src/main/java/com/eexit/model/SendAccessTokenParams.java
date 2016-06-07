package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class SendAccessTokenParams {

    @SerializedName("token") private String token;
    @SerializedName("country") private String country;
    @SerializedName("city") private String city;

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }


    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public SendAccessTokenParams(String token) {
	this.token = token;
    }

    public SendAccessTokenParams(String token, String country) {
	this.token = token;
	this.country = country;
    }

    public SendAccessTokenParams(String token, String country, String city) {
	this.token = token;
	this.country = country;
	this.city = city;
    }

    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }
}
