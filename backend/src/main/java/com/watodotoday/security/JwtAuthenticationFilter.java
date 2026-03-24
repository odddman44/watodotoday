package com.watodotoday.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
			throws ServletException, IOException {

		String token = extractToken( request );

		if (token != null && jwtTokenProvider.isValid( token )) {
			Long userId = jwtTokenProvider.extractUserId( token );
			// principal 자리에 userId를 넣어두면, 컨트롤러에서 꺼내 쓸 수 있음.
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken( userId, null, List.of() );
			SecurityContextHolder.getContext().setAuthentication( auth );
		}
		filterChain.doFilter( request, response );
	}

	private String extractToken( HttpServletRequest request ) {

		if (request.getCookies() == null) return null;
		for( Cookie cookie : request.getCookies() ) {
			if("access_token".equals( cookie.getName() )) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
