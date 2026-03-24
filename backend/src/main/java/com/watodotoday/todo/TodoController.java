package com.watodotoday.todo;

import com.watodotoday.todo.dto.TodoRequest;
import com.watodotoday.todo.dto.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@PostMapping
	public ResponseEntity<TodoResponse> create(
			@AuthenticationPrincipal Long userId,
			@RequestBody TodoRequest request ) {

		return ResponseEntity.ok( todoService.create( userId, request ) );
	}

	@GetMapping
	public ResponseEntity<List<TodoResponse>> getAll(
			@AuthenticationPrincipal Long userId,
			@RequestParam(defaultValue = "all") String filter) {

		return ResponseEntity.ok( todoService.getAll( userId, filter ) );
	}

	@PatchMapping("/{id}")
	public ResponseEntity<TodoResponse> update(
			@AuthenticationPrincipal Long userId,
			@PathVariable Long id,
			@RequestBody TodoRequest request ) {

		return ResponseEntity.ok( todoService.update( userId, id, request ) );
	}

	@PatchMapping("/{id}/complete")
	public ResponseEntity<TodoResponse> complete(
			@AuthenticationPrincipal Long userId,
			@PathVariable Long id ) {

		return ResponseEntity.ok( todoService.complete( userId, id ) );
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
			@AuthenticationPrincipal Long userId,
			@PathVariable Long id ) {

		todoService.delete( userId, id );
		return ResponseEntity.noContent().build();
	}
}
