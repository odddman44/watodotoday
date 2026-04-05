package com.watodotoday.auth;

import com.watodotoday.auth.dto.LoginRequest;
import com.watodotoday.auth.dto.MeResponse;
import com.watodotoday.auth.dto.SignupRequest;
import com.watodotoday.domain.user.User;
import com.watodotoday.domain.user.UserRepository;
import com.watodotoday.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public void signup( SignupRequest request ) {
		if (userRepository.existsByEmail( request.email() )) {
			throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
		}
		User user = User.create(
				request.email(),
				passwordEncoder.encode( request.password() ),
				request.nickname()
		);
		userRepository.save( user );
	}

	public String login( LoginRequest request ) {

		User user = userRepository.findByEmail( request.email() )
								  .orElseThrow( () -> new IllegalArgumentException( "이메일 또는 비밀번호가 올바르지 않습니다." ) );

		if( !passwordEncoder.matches( request.password(), user.getPassword() ) ) {
			throw new IllegalArgumentException( "이메일 또는 비밀번호가 올바르지 않습니다." );
		}

		return jwtTokenProvider.generate( user.getId() );
	}

	public MeResponse me(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("유저 없음"));
		return new MeResponse( user.getId(), user.getEmail(), user.getNickname());
	}
}
