package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class ChangeDestinationParams {

    @SerializedName("destination") private String destination;

    public String getDestination() {
	return destination;
    }

    public void setDestination(String destination) {
	this.destination = destination;
    }

    public ChangeDestinationParams(String destination) {
	this.destination = destination;
    }
}
