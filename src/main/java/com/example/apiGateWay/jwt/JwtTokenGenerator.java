package com.example.apiGateWay.jwt;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenGenerator {

	private String tokenIssuer = "One More Light";
	
	@Value("${application.jwt.token.secret}")
	private String tokenSecret;
	
	private String tokenSubject = "One More Light - access toke";
	
	 public String generateToken() {
		 
	        Claims claims = Jwts.claims()
	                .setSubject(tokenSubject);
	        claims.put("userName", "socialUser");
	        claims.put("userEmail", "socialEEmail");
	        claims.put("userType", "socialType");

	        return Jwts.builder()
	                .setClaims(claims)
	                .setIssuedAt(new Date())
	                .setIssuer(tokenIssuer)
	                .signWith(SignatureAlgorithm.HS512, tokenSecret)
	                .compact();
	    }
}
