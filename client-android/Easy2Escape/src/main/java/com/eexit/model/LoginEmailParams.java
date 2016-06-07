package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class LoginEmailParams {

    @SerializedName("email") private String email;
    @SerializedName("password") private String password;

    public LoginEmailParams(String email, String password) {
	this.email = email;
	this.password = password;
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
}
