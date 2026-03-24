package com.watodotoday.todo;

import com.watodotoday.domain.todo.Priority;
import com.watodotoday.domain.todo.TodoItem;
import com.watodotoday.domain.todo.TodoRepository;
import com.watodotoday.domain.user.User;
import com.watodotoday.domain.user.UserRepository;
import com.watodotoday.security.JwtTokenProvider;
import com.watodotoday.todo.dto.TodoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TodoControllerTest {

	private MockMvc mockMvc;

	@Autowired private WebApplicationContext context;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private UserRepository userRepository;
	@Autowired private TodoRepository todoRepository;
	@Autowired private JwtTokenProvider jwtTokenProvider;
	@Autowired private PasswordEncoder passwordEncoder;

	private String token;
	private User savedUser;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup( context )
				.apply( SecurityMockMvcConfigurers.springSecurity() )
				.build();
		todoRepository.deleteAll();
		userRepository.deleteAll();

		savedUser = userRepository.save(
				User.create( "test@test.com", passwordEncoder.encode( "password123" ), "테스터" )
		);
		token = jwtTokenProvider.generate( savedUser.getId() );
	}

	private ResultActions perform( RequestBuilder request) throws Exception {

		return mockMvc.perform( request );
	}

	private UsernamePasswordAuthenticationToken authToken() {
		return new UsernamePasswordAuthenticationToken( savedUser.getId(), null, List.of() );
	}

	@Test
	void 할일을_생성한다() throws Exception {

		TodoRequest request = new TodoRequest( "테스트 할 일", "설명", Priority.MEDIUM, null );

		perform( post("/api/todos")
				.with(authentication(authToken()))
				.contentType( MediaType.APPLICATION_JSON )
				.content(objectMapper.writeValueAsString( request )))
				.andExpect( status().isOk() )
				.andExpect( jsonPath("$.title").value("테스트 할 일") )
				.andExpect( jsonPath( "$.status" ).value( "TODO" ) );
	}

	@Test
	void 인증_없이_요청하면_401() throws Exception {
		perform( get("/api/todos") )
				.andExpect( status().isUnauthorized() );
	}

	@Test
	void 할일_목록을_조회한다() throws Exception {
		todoRepository.save( TodoItem.create( savedUser, "할 일 1", null, Priority.LOW, null ) );
		todoRepository.save( TodoItem.create( savedUser, "할 일 2", null, Priority.HIGH, null ) );

		perform( get("/api/todos")
				.with(authentication(authToken())))
				.andExpect(status().isOk())
				.andExpect( jsonPath( "$.length()" ).value( 2) );

	}

	@Test
	void 할일을_수정한다() throws Exception {
		TodoItem item = todoRepository.save(
				TodoItem.create( savedUser, "원래 제목", null, Priority.LOW, null ) );

		TodoRequest request = new TodoRequest( "수정된 제목", null, Priority.HIGH, null );

		perform( patch("/api/todos/" + item.getId())
				.with(authentication(authToken()))
				.contentType( MediaType.APPLICATION_JSON )
				.content( objectMapper.writeValueAsString( request ) ))
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.title" ).value( "수정된 제목" ) )
				.andExpect( jsonPath( "$.priority" ).value( "HIGH" ) );

	}

	@Test
	void 할일을_완료_처리한다() throws Exception {
		TodoItem item = todoRepository.save(
				TodoItem.create(savedUser, "테스트 할 일", null, Priority.MEDIUM, null));

		perform( patch( "/api/todos/" + item.getId() + "/complete" )
				.with(authentication(authToken())) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.status" ).value( "DONE" ) );
	}

	@Test
	void 할일을_삭제한다() throws Exception {
		TodoItem item = todoRepository.save(
				TodoItem.create( savedUser, "삭제할 할 일", null, Priority.LOW, null ) );

		perform( delete( "/api/todos/" + item.getId() )
				.with(authentication(authToken())) )
				.andExpect( status().isNoContent() );
	}

	@Test
	void 다른_유저의_할일은_수정_불가() throws Exception {
		User otherUser = userRepository.save(
				User.create( "other@test.com", passwordEncoder.encode( "password123" ), "다른유저" ) );
		TodoItem otherItem = todoRepository.save(
				TodoItem.create( otherUser, "다른 유저의 할 일", null, Priority.LOW, null )
		);

		TodoRequest request = new TodoRequest( "해킹 시도", null, Priority.HIGH, null );

		perform( patch( "/api/todos/" + otherItem.getId() )
				.with(authentication(authToken()))
				.contentType( MediaType.APPLICATION_JSON )
				.content( objectMapper.writeValueAsString( request ) ) )
				.andExpect( status().isBadRequest() );
	}

}
