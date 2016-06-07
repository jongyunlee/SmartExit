package com.eexit.model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class ChangeDirectionParams {

    @SerializedName("direction") private String direction;

    public String getDirection() {
	return direction;
    }

    public void setDirection(String direction) {
	this.direction = direction;
    }

    public ChangeDirectionParams(String direction) {
	this.direction = direction;
    }
}
