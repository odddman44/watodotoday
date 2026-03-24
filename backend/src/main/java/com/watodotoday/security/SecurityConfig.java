package com.watodotoday.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

		http.csrf( csrf -> csrf.disable() )
			.sessionManagement( session ->
					session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
			.exceptionHandling( e -> e
					.authenticationEntryPoint( (req, res, ex ) ->
							res.sendError( HttpServletResponse.SC_UNAUTHORIZED )))
			.authorizeHttpRequests( auth -> auth
					.requestMatchers( "/api/auth/**" ).permitAll()
					.anyRequest().authenticated()
			)
			.addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
		return http.build();
	}

	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterRegistration(JwtAuthenticationFilter filter) {
		FilterRegistrationBean<JwtAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled( false );

		return registration;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
