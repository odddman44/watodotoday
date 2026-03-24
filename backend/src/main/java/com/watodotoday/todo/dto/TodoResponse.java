package com.watodotoday.todo.dto;

import com.watodotoday.domain.todo.Priority;
import com.watodotoday.domain.todo.TodoItem;
import com.watodotoday.domain.todo.TodoStatus;

import java.time.LocalDateTime;

public record TodoResponse(Long id, String title, String description, TodoStatus status, Priority priority, LocalDateTime dueDate,
						   LocalDateTime createdAt) {

	public static TodoResponse from( TodoItem item ) {
		return new TodoResponse(
				item.getId(),
				item.getTitle(),
				item.getDescription(),
				item.getStatus(),
				item.getPriority(),
				item.getDueDate(),
				item.getCreatedAt()
		);
	}
}
