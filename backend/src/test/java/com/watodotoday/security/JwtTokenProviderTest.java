package com.watodotoday.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

	private JwtTokenProvider jwtTokenProvider;

	@BeforeEach
	void setUp() {
		String secret = "test-secret-key-for-testing-must-be-32-chars-min";
		long expiryMs = 3600000L; // 1시간
		jwtTokenProvider = new JwtTokenProvider(secret, expiryMs);
	}

	@Test
	void 토큰을_생성하면_userId를_추출할_수_있다() {
		Long userId = 1L;

		String token = jwtTokenProvider.generate(userId);
		Long extracted = jwtTokenProvider.extractUserId(token);

		assertThat( extracted ).isEqualTo( userId );
	}

	@Test
	void 유효한_토큰은_isValid가_true를_반환한다() {
		String token = jwtTokenProvider.generate(1L);
		assertThat( jwtTokenProvider.isValid(token) ).isTrue();
	}

	@Test
	void 잘못된_토큰은_isValid가_false를_반환한다() {
		assertThat( jwtTokenProvider.isValid("wrong-token") ).isFalse();
	}
}
