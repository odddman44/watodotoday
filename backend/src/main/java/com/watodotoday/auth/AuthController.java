package com.watodotoday.auth;

import com.watodotoday.auth.dto.LoginRequest;
import com.watodotoday.auth.dto.SignupRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/auth" )
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping( "/signup" )
	public ResponseEntity<Void> signup( @RequestBody SignupRequest request ) {

		authService.signup( request );
		return ResponseEntity.ok().build();
	}

	@PostMapping( "/login" )
	public ResponseEntity<Void> login( @RequestBody LoginRequest request, HttpServletResponse response ) {

		String token = authService.login( request );
		Cookie cookie = new Cookie( "access_token", token );
		cookie.setHttpOnly( true );
		cookie.setPath( "/" );
		cookie.setMaxAge( 7 * 24 * 60 * 60 ); // 7일
		response.addCookie( cookie );
		return ResponseEntity.ok().build();
	}

	@PostMapping( "/logout" )
	public ResponseEntity<Void> logout( HttpServletResponse response ) {

		Cookie cookie = new Cookie( "access_token", "" );
		cookie.setHttpOnly( true );
		cookie.setPath( "/" );
		cookie.setMaxAge( 0 ); // 즉시 만료
		response.addCookie( cookie );
		return ResponseEntity.ok().build();
	}
}
