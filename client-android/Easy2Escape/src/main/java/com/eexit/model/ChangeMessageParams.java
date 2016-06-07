package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class ChangeMessageParams {

    @SerializedName("message") private String message;

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public ChangeMessageParams(String message) {
	this.message = message;
    }
}
