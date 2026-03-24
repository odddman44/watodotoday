package com.watodotoday.auth;

import com.watodotoday.auth.dto.LoginRequest;
import com.watodotoday.auth.dto.SignupRequest;
import com.watodotoday.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup( context )
				.apply( SecurityMockMvcConfigurers.springSecurity() )
				.build();
		userRepository.deleteAll();
	}

	@Test
	void 회원가입_성공() throws Exception {

		SignupRequest request = new SignupRequest( "test@test.com", "password123", "테스터" );

		mockMvc.perform( post( "/api/auth/signup" )
					   .contentType( MediaType.APPLICATION_JSON )
					   .content( objectMapper.writeValueAsString( request ) ) )
			   .andExpect( status().isOk() );
	}

	@Test
	void 중복_이메일_회원가입_실패() throws Exception {
		SignupRequest request = new SignupRequest( "test@test.com", "password123", "테스터" );

		mockMvc.perform( post( "/api/auth/signup" )
					   .contentType( MediaType.APPLICATION_JSON )
					   .content( objectMapper.writeValueAsString( request ) ) )
			   .andExpect( status().isOk() );

		mockMvc.perform( post( "/api/auth/signup" )
						.contentType( MediaType.APPLICATION_JSON )
						.content( objectMapper.writeValueAsString( request ) ) )
				.andExpect( status().isBadRequest() );
	}

	@Test
	void 로그인_성공_후_쿠키_발급() throws Exception {
		SignupRequest signup = new SignupRequest( "test@test.com", "password123", "테스터" );
		mockMvc.perform( post( "/api/auth/signup" )
						.contentType( MediaType.APPLICATION_JSON )
						.content( objectMapper.writeValueAsString( signup ) ) )
				.andExpect( status().isOk() );

		LoginRequest login = new LoginRequest( "test@test.com", "password123" );
		mockMvc.perform( post( "/api/auth/login" )
						.contentType( MediaType.APPLICATION_JSON )
						.content( objectMapper.writeValueAsString( login ) ) )
				.andExpect( status().isOk() )
				.andExpect( cookie().exists("access_token") );
	}

	@Test
	void 틀린_비밀번호_로그인_실패() throws Exception {
		SignupRequest signup = new SignupRequest("test@test.com", "password123", "테스터");
		mockMvc.perform(post("/api/auth/signup")
					   .contentType(MediaType.APPLICATION_JSON)
					   .content(objectMapper.writeValueAsString(signup)))
			   .andExpect(status().isOk());

		LoginRequest login = new LoginRequest("test@test.com", "wrong-password");
		mockMvc.perform(post("/api/auth/login")
					   .contentType(MediaType.APPLICATION_JSON)
					   .content(objectMapper.writeValueAsString(login)))
			   .andExpect(status().isBadRequest());
	}
}
