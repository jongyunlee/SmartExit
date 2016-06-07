package com.eexit.model;

import com.google.gson.annotations.SerializedName;

public class AlertMessage {

    @SerializedName("title") private String title;
    @SerializedName("sticky") private boolean sticky;
    @SerializedName("cancelable") private boolean cancelable;
    @SerializedName("action") private String action;
    @SerializedName("message_id") private String message_id;
    @SerializedName("message") private String message;

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getMessage_Id() {
	return message_id;
    }

    public void setMessage_Id(String message_id) {
	this.message_id = message_id;
    }


    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }


    public boolean getCancelable() {
	return cancelable;
    }

    public void setCancelable(boolean cancelable) {
	this.cancelable = cancelable;
    }


    public boolean getSticky() {
	return sticky;
    }

    public void setSticky(boolean sticky) {
	this.sticky = sticky;
    }


    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

}
