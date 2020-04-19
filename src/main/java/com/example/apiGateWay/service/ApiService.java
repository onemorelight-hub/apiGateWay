package com.example.apiGateWay.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.apiGateWay.model.SignupData;
import com.example.apiGateWay.model.StatusMessage;
import com.example.apiGateWay.model.Token;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class ApiService {
	
	private String userSeriveBaseUrl="http://user-service:2020";

	public StatusMessage signUpUser(SignupData user) throws UnirestException {
		StatusMessage statusMessage = new StatusMessage();
		Map<String, String> headers = new HashMap<>();
	    headers.put("Content-Type", "application/x-www-form-urlencoded");
	    
	    Map<String, Object> fields = new HashMap<>();
	    fields.put("firstName", user.getFirstName());
	    fields.put("lastName", user.getPassword());
	    fields.put("email", user.getEmail());
	    fields.put("userType", user.getUserType());
	    fields.put("password", user.getPassword());
	  
	    HttpResponse<JsonNode> jsonResponse
	      = Unirest.post(userSeriveBaseUrl+ "/loginservice/signup")
	      .headers(headers).fields(fields)
	      .asJson();
	    statusMessage.setMessage(jsonResponse.getBody().getObject().getString("meaasge"));
	    statusMessage.setStatus(jsonResponse.getStatus());
		return statusMessage;
	}

	public boolean verifyUser(String id, String verifyCode) throws UnirestException {
		
		HttpResponse<JsonNode> jsonResponse 
		  = Unirest.get(userSeriveBaseUrl+ "/loginservice/verify/{id}/{code}")
		  .routeParam("id", id)
		  .routeParam("code", verifyCode).asJson();
		
		if(jsonResponse.getStatus() == 200) {
			return true;
		}
		return false;
	}

	public Token login(String base64Credentials) throws UnirestException {
		
		Token token = new Token();
		
		Map<String, String> headers = new HashMap<>();
	    headers.put("Authorization", base64Credentials);

	    HttpResponse<JsonNode> jsonResponse 
	      = Unirest.post(userSeriveBaseUrl+ "/loginservice/login")
	      .headers(headers)
	      .asJson(); 
	    
	    token.setStatus(jsonResponse.getStatus());
	    token.setMessage(jsonResponse.getBody().getObject().getString("meaasge"));
	    if(jsonResponse.getStatus() == 202) {
	    	token.setToken(jsonResponse.getBody().getObject().getString("Token"));
	    }
	    return token;
	}
	
	public boolean verifyFacebookAuthToken(String authToken) throws UnirestException {
		
		HttpResponse<JsonNode> jsonResponse 
		  = Unirest.get("https://graph.facebook.com/me")
		  .queryString("access_token", authToken).asJson();
		
		if(jsonResponse.getStatus() == 200) {
			return true;
		}
		return false;
	}
	
	public boolean verifyGoogleAuthToken(String authToken) throws UnirestException {
		
		HttpResponse<JsonNode> jsonResponse 
		  = Unirest.get("https://www.googleapis.com/oauth2/v1/tokeninfo")
		  .queryString("access_token", authToken).asJson();
		
		if(jsonResponse.getStatus() == 200) {
			return true;
		}
		return false;
	}

	public boolean verifyAndStoreSocialUser(Map<String, Object> user) throws UnirestException {
		SignupData data = new SignupData();
		data.setEmail(user.get("email").toString());
		data.setUserId(user.get("id").toString());
		data.setFirstName(user.get("firstName").toString());
		data.setPassword("MydefinedPassword");
		//signUpUser(data);
		if(user.get("provider").toString().equalsIgnoreCase("FACEBOOK")) {
			if(verifyFacebookAuthToken(user.get("authToken").toString())) {
				return true;
			}
		}
		if(user.get("provider").toString().equalsIgnoreCase("Google")) {
			if(verifyGoogleAuthToken(user.get("authToken").toString())) {
				return true;
			}
		}
		return false;
	}
}
