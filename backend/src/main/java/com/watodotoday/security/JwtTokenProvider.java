package com.watodotoday.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

	private final SecretKey key;
	private final long expiryMs;

	public JwtTokenProvider( @Value( "${app.jwt.secret}" ) String secret, @Value( "${app.jwt.expiration-ms}" ) long expiryMs ) {

		this.key = Keys.hmacShaKeyFor( secret.getBytes() );
		this.expiryMs = expiryMs;
	}

	public String generate( Long userId ) {

		return Jwts.builder()
				   .subject( String.valueOf( userId ) )
				   .issuedAt( new Date() )
				   .expiration( new Date( System.currentTimeMillis() + expiryMs ) )
				   .signWith( key )
				   .compact();
	}

	public Long extractUserId( String token ) {

		return Long.parseLong(
				Jwts.parser()
					   .verifyWith( key )
					   .build()
					   .parseSignedClaims( token )
					   .getPayload()
					   .getSubject()
		);
	}

	public boolean isValid( String token ) {
		try {
			Jwts.parser().verifyWith( key ).build().parseSignedClaims( token );
			return true;
		} catch ( JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
