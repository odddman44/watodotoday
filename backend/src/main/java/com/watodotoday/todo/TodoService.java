package com.watodotoday.domain.todo;

import com.watodotoday.domain.todo.dto.TodoRequest;
import com.watodotoday.domain.todo.dto.TodoResponse;
import com.watodotoday.domain.user.User;
import com.watodotoday.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

	private final TodoRepository todoRepository;
	private final UserRepository userRepository;

	@Transactional
	public TodoResponse create(Long userId, TodoRequest request) {
		User user = userRepository.findById(userId)
								  .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		TodoItem item = TodoItem.create(
				user,
				request.title(),
				request.description(),
				request.priority(),
				request.dueDate()
		);
		return TodoResponse.from( todoRepository.save( item ) );
	}

	@Transactional(readOnly = true)
	public List<TodoResponse> getAll(Long userId, String filter) {
		List<TodoItem> items = switch( filter ) {
			case "completed" -> todoRepository.findByUserIdAndStatus( userId, TodoStatus.DONE );
			case "incomplete" -> todoRepository.findByUserIdAndStatus( userId, TodoStatus.TODO );
			case "today" -> {
				LocalDate today = LocalDate.now();
				yield todoRepository.findByUserIdAndDueDateBetween(
						userId,
						today.atStartOfDay(),
						today.plusDays( 1 ).atStartOfDay()
				);
			}
			default -> todoRepository.findByUserId( userId );
		};

		return items.stream()
				.map( TodoResponse::from )
				.toList();
	}

	@Transactional
	public TodoResponse update(Long userId, Long todoId, TodoRequest request) {
		TodoItem item = findOwnedItem(userId, todoId);
		item.update( request.title(), request.description(), request.priority(), request.dueDate() );
		return TodoResponse.from( item );
	}

	@Transactional
	public void delete(Long userId, Long todoId) {
		TodoItem item = findOwnedItem(userId, todoId);
		todoRepository.delete( item );
	}


	@Transactional
	public TodoResponse complete(Long userId, Long todoId) {
		TodoItem item = findOwnedItem(userId, todoId);
		item.complete();
		return TodoResponse.from( item );
	}

	private TodoItem findOwnedItem( Long userId, Long todoId ) {

		TodoItem item = todoRepository.findById( todoId )
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할 일입니다."));
		if (!item.getUser().getId().equals( userId )) {
			throw new IllegalArgumentException("본인의 할 일만 수정할 수 있습니다.");
		}
		return item;
	}

}
