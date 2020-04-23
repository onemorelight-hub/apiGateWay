package com.example.apiGateWay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiGateWay.jwt.JwtTokenGenerator;
import com.example.apiGateWay.model.SignupData;
import com.example.apiGateWay.model.StatusMessage;
import com.example.apiGateWay.model.Token;
import com.example.apiGateWay.service.ApiService;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping (value ="/api")
public class ApiController {

	@Autowired
	JwtTokenGenerator jwtTokenGenerator;
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	private HttpServletRequest httpRequest;
	
	@PostMapping (value = "/social/createToken")
	public ResponseEntity<Map<String, Object>> createTokenFacebookUser(@RequestBody Map<String, Object> user) throws UnirestException {
		System.out.println("Facebook user data"+ user);
		Map<String, Object> object = new HashMap<>();
		if(apiService.verifyAndStoreSocialUser(user)) {
			String token = jwtTokenGenerator.generateToken();
			object.put("token", token);
			return ResponseEntity.status(HttpStatus.OK).body(object);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(object);
	}
	
	
	@PostMapping (value = "/mobile/createToken")
	public ResponseEntity<Map<String, Object>> createTokenGoogleUser(@RequestBody Map<String, Object> user) {
		System.out.println("Mobile user data"+ user);
		Map<String, Object> object = new HashMap<>();
		String token = jwtTokenGenerator.generateToken();
		object.put("token", token);
		return ResponseEntity.status(HttpStatus.OK).body(object);
	}
	
	/**
	 * This Method is to register new user request.
	 * @param user
	 * @return
	 * @throws UnirestException 
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping (value = "/signup",  consumes = "application/x-www-form-urlencoded")
	public ResponseEntity signUp(SignupData user) throws UnirestException {
		System.out.println("LoginController.signUp(): first_name: "+user.getFirstName());
		System.out.println("LoginController.signUp(): Email: "+user.getEmail());
		System.out.println("LoginController.signUp(): id: "+user.getUserId());
		StatusMessage statusMessage = apiService.signUpUser(user);
		return ResponseEntity.status(statusMessage.getStatus()).body(statusMessage);
	}
	
	/**
	 * This method verify a user details to activate user
	 * @param id
	 * @param verifyCode
	 * @return
	 * @throws UnirestException 
	 */
	@GetMapping (value ="/verify/{id}/{verifyCode}")
	public String verfyUser(@PathVariable String id, @PathVariable String verifyCode) throws UnirestException {
		if(apiService.verifyUser(id, verifyCode)) {
			return "User verified!";
		}
		return "Invalid request";
	}
	
	/**
	 * This Method is for handling login requests 
	 * @param user : user login info
	 * @return
	 * @throws UnirestException 
	 */
	@ResponseBody
	@RequestMapping (value = "/login" , method = RequestMethod.POST)
	public ResponseEntity<Token> signIn() throws UnirestException {
		final String authorization = httpRequest.getHeader("Authorization");
		Token token = apiService.login(authorization);
		return ResponseEntity.status(token.getStatus()).body(token);
	}
}
