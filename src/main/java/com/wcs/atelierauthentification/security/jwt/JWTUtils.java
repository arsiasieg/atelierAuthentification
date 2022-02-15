package com.wcs.atelierauthentification.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.wcs.atelierauthentification.security.service.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {
	
	@Value("${com.wcs.atelierauthentification.jwtExpirationMs}")
	private int expirationMs;
	
	@Value("${com.wcs.atelierauthentification.secretKey}")
	private String secretKey;
	
	public String generateToken(Authentication authentification) {
		//Construction d'un userDetailsImpl via l'auth (= user mais compréhensif par Spring)
	 	UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentification.getPrincipal();
		
		return Jwts.builder()
		.setSubject(userDetailsImpl.getUsername())  						//le token doit être construit avec mon user
		.setIssuedAt(new Date()) 											//date de création du token
		.setExpiration(new Date((new Date()).getTime() + this.expirationMs))//on va cherche la date actuelle (en ms) + le temps d'expiration -> date où le token va expirer
		.signWith(SignatureAlgorithm.HS512, secretKey)						//création de la signature via la jwtSecret du app.properties et l'encodeur (algorithm HS512)
		.compact();
	}
}
