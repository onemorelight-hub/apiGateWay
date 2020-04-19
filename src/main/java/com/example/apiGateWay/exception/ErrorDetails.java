package com.example.apiGateWay.exception;

import java.io.Serializable;
import java.time.Instant;
/**
 * Error Custom Exception Object from API-Service 
 * @author anjanjana
 *
 */
public class ErrorDetails implements Serializable{
	private static final long serialVersionUID = -4214786648499254708L;
	private long timestamp;
	private int statusCode;
	private String errorMessage;
	private String moreDetails;
	public ErrorDetails(int statusCode, String error, String moreDetails) {
		this.statusCode = statusCode;
		this.errorMessage = error;
		this.moreDetails = moreDetails;
		this.timestamp = Instant.now().getEpochSecond();
	}
	public int getStatusCode() {
		return statusCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public String getMoreDetails() {
		return moreDetails;
	}
}
