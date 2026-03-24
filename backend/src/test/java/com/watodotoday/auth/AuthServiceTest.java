package com.watodotoday.auth;

import com.watodotoday.auth.dto.LoginRequest;
import com.watodotoday.auth.dto.SignupRequest;
import com.watodotoday.domain.user.User;
import com.watodotoday.domain.user.UserRepository;
import com.watodotoday.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith( MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@InjectMocks
	private AuthService authService;

	@Test
	void 새로운_이메일로_회원가입하면_저장된다() {
		SignupRequest request = new SignupRequest("test@test.com", "password123", "테스터");
		given(userRepository.existsByEmail( "test@test.com" )).willReturn(false);
		given(passwordEncoder.encode( "password123" )).willReturn("hashed-password");

		authService.signup( request );

		verify( userRepository ).save( any( User.class) );
	}

	@Test
	void 중복_이메일로_회원가입하면_예외가_발생한다() {

		SignupRequest request = new SignupRequest( "test@test.com", "password123", "테스터" );
		given( userRepository.existsByEmail( "test@test.com" ) ).willReturn( true );

		assertThatThrownBy( () -> authService.signup( request ) )
				.isInstanceOf( IllegalArgumentException.class )
				.hasMessage( "이미 사용중인 이메일입니다." );
	}

	@Test
	void 올바른_비밀번호로_로그인하면_토큰이_반환된다() {

		LoginRequest request = new LoginRequest( "test@test.com", "password123" );
		User user = User.create( "test@test.com", "hashed-password", "테스터" );
		given( userRepository.findByEmail( "test@test.com" ) ).willReturn( Optional.of( user ) );
		given( passwordEncoder.matches( "password123", "hashed-password" )).willReturn( true );
		given( jwtTokenProvider.generate( any() ) ).willReturn( "jwt-token" );

		String token = authService.login( request );

		assertThat(token).isEqualTo("jwt-token");
	}

	@Test
	void 틀린_비밀번호로_로그인하면_예외가_발생한다() {

		LoginRequest request = new LoginRequest( "test@test.com", "wrong-password" );
		User user = User.create( "test@test.com", "hashed-password", "테스터" );
		given(userRepository.findByEmail( "test@test.com" )).willReturn( Optional.of( user ) );
		given( passwordEncoder.matches( "wrong-password", "hashed-password" ) ).willReturn( false );

		assertThatThrownBy( () -> authService.login( request ) )
				.isInstanceOf( IllegalArgumentException.class )
				.hasMessage( "이메일 또는 비밀번호가 올바르지 않습니다." );
	}
}
