package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class ChangeStateParams {

    @SerializedName("state") private String state;

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public ChangeStateParams(String state) {
	this.state = state;
    }
}
