package com.example.apiGateWay.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenValidator {

	@Value("${application.jwt.token.secret}")
	private String tokenSecret;
	
	  public boolean validate(String token) {

	    	System.out.println("JwtValidator.validate()");
	    boolean status = false;
	    	try {
	            Jwts.parser()
	                    .setSigningKey(tokenSecret)
	                    .parseClaimsJws(token)
	                    .getBody();
	            status = true;
	          
	        }
	        catch (Exception e) {
	            System.out.println(e);
	        }

	        return status;
	    }
}
