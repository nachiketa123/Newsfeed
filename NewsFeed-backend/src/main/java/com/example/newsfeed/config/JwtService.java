package com.example.newsfeed.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.newsfeed.DTO.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	@Value("${jwt.secretKey}")
	private String jwtSecretKey;
	
	/*
	 * Token generation related methods
	 * */
	
	public String generateToken(UserDTO user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("email", user.getEmail());
		claims.put("name", user.getName());
		return createTokenUsingClaims(claims,user.getEmail());
	}
	

	private String createTokenUsingClaims(Map<String, Object> claims, String username) {
		
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(username)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) //1 hour
			.signWith(getJwtSecretKey(),SignatureAlgorithm.HS256)
			.compact();

	}

	private Key getJwtSecretKey() {
		byte[] secretKeyBytes = Decoders.BASE64.decode(jwtSecretKey);		
		return Keys.hmacShaKeyFor(secretKeyBytes);
	}
	
	
	/*
	 * Extraction related methods below
	 * */
	
	private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getJwtSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	public String extractUserDetailsFromToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /*
     * To check if the token is valid or not
     * */
    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserDetailsFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
