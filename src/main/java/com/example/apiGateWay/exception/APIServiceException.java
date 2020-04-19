package com.example.apiGateWay.exception;
/**
 * API service Custom exception exception 
 * @author anjanjana
 *
 */
public class APIServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	private ErrorDetails errorDetails;
	public APIServiceException(int statusCode, String message, String moreDetails) {
		errorDetails = new ErrorDetails(statusCode,message, moreDetails);
	}
	public ErrorDetails getErrorDetails() {
		return errorDetails;
	}
}
