package com.watodotoday.todo;

import com.watodotoday.domain.todo.Priority;
import com.watodotoday.domain.todo.TodoItem;
import com.watodotoday.domain.todo.TodoRepository;
import com.watodotoday.domain.todo.TodoStatus;
import com.watodotoday.domain.user.User;
import com.watodotoday.domain.user.UserRepository;
import com.watodotoday.todo.dto.TodoRequest;
import com.watodotoday.todo.dto.TodoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith( MockitoExtension.class)
public class TodoServiceTest {

	@Mock
	private TodoRepository todoRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private User mockUser;

	@InjectMocks
	private TodoService todoService;

//	private User user;

	private TodoItem todoItem;

	@BeforeEach
	void setUp() {
//		user = User.create("test@test.com", "hashed-password", "테스터");
//		todoItem = TodoItem.create(user, "테스트 할 일", "설명", Priority.MEDIUM, null);
//		given(mockUser.getId()).willReturn(1L);
		todoItem = TodoItem.create( mockUser, "테스트 할 일", "설명", Priority.MEDIUM, null );
	}

	@Test
	void 할일을_생성하면_저장된다() {

		TodoRequest request = new TodoRequest( "테스트 할 일", "설명", Priority.MEDIUM, null );
//		given( userRepository.findById(1L)).willReturn( Optional.of( user ) );
		given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));

		given(todoRepository.save(any(TodoItem.class))).willReturn(todoItem);

		TodoResponse response = todoService.create(1L, request);

		assertThat(response.title()).isEqualTo( "테스트 할 일" );
		verify( todoRepository ).save( any( TodoItem.class ) );
	}

	@Test
	void 존재하지_않는_유저로_할일_생성시_예외가_발생한다() {
		TodoRequest request = new TodoRequest( "테스트 할 일", "설명", Priority.MEDIUM, null );
		given(userRepository.findById( 999L )).willReturn( Optional.empty() );

		assertThatThrownBy( () -> todoService.create( 999L, request ) )
				.isInstanceOf( IllegalArgumentException.class )
				.hasMessage( "존재하지 않는 사용자입니다." );
	}

	@Test
	void 전체_목록을_조회한다() {
		given(todoRepository.findByUserId( 1L )).willReturn( List.of( todoItem ) );

		List<TodoResponse> result = todoService.getAll( 1L, "all" );

		assertThat( result ).hasSize( 1 );
		assertThat( result.get( 0 ).title() ).isEqualTo( "테스트 할 일" );
	}

	@Test
	void 완료된_할일만_조회한다() {

		given( todoRepository.findByUserIdAndStatus( 1L, TodoStatus.DONE ) ).willReturn( List.of() );

		List<TodoResponse> result = todoService.getAll( 1L, "completed" );

		assertThat( result ).isEmpty();
	}

	@Test
	void 할일을_수정한다() {
		given(mockUser.getId()).willReturn(1L);
		TodoRequest request = new TodoRequest( "수정된 제목", "수정된 설명", Priority.HIGH, null );
		given(todoRepository.findById( 1L )).willReturn( Optional.of( todoItem ) );
		// user.getId()가 1L을 반환하도록 실제 User 대신 id가 세팅된 객체가 필요하나,
		// 여기서는 같은 user 객체를 쓰므로 getId()가 null → null.equals()로 예외 발생 가능
		// 따라서 다른 user 객체와의 소유권 검증은 아래 별도 테스트로 분리

		// 소유권 검증: item.getUser().getId().equals(userId)
		// user.getId()는 DB 저장 전이라 null → null.equals(null) → true
//		TodoResponse response = todoService.update( null, 1L, request );
		TodoResponse response = todoService.update(1L, 1L, request);

		assertThat( response.title() ).isEqualTo( "수정된 제목" );
		assertThat( response.priority() ).isEqualTo( Priority.HIGH );
	}

	@Test
	void 존재하지_않는_할일_수정시_예외가_발생한다() {
		TodoRequest request = new TodoRequest( "수정된 제목", "설명", Priority.HIGH, null );
		given(todoRepository.findById( 999L )).willReturn( Optional.empty() );

		assertThatThrownBy( () -> todoService.update( 1L, 999L, request ) )
				.isInstanceOf( IllegalArgumentException.class )
				.hasMessage( "존재하지 않는 할 일입니다." );
	}

	@Test
	void 할일을_삭제한다() {
		given(mockUser.getId()).willReturn(1L);
		given(todoRepository.findById( 1L )).willReturn( Optional.of( todoItem ) );

//		todoService.delete( null, 1L );
		todoService.delete(1L, 1L);
		verify( todoRepository ).delete(todoItem);

	}

	@Test
	void 할일을_완료_처리한다() {
		given(mockUser.getId()).willReturn(1L);
		given(todoRepository.findById( 1L )).willReturn( Optional.of( todoItem ) );

//		TodoResponse response = todoService.complete( null, 1L );
		TodoResponse response = todoService.complete(1L, 1L);
		assertThat(response.status()).isEqualTo(TodoStatus.DONE);
	}
}
