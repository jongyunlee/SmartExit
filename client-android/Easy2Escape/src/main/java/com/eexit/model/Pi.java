package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class Pi {

    @SerializedName("_id") private String id;
    @SerializedName("message") private String message;
    @SerializedName("temp") private float temp;
    @SerializedName("humidity") private float humidity;
    @SerializedName("direction") private String direction;
    @SerializedName("location") private String location;
    @SerializedName("state") private String state;
    @SerializedName("destination") private String destination;

    public String getDestination() {
	return destination;
    }

    public void setDestination(String destination) {
	this.destination = destination;
    }


    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }


    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }


    public String getDirection() {
	return direction;
    }

    public void setDirection(String direction) {
	this.direction = direction;
    }


    public float getHumidity() {
	return humidity;
    }

    public void setHumidity(float humidity) {
	this.humidity = humidity;
    }


    public float getTemp() {
	return temp;
    }

    public void setTemp(float temp) {
	this.temp = temp;
    }


    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }


    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }
}
