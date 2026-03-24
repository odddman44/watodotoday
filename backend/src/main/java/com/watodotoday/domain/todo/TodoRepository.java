package com.watodotoday.domain.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {

	List<TodoItem> findByUserId(Long userId);

	List<TodoItem> findByUserIdAndStatus(Long userId, TodoStatus status);

	List<TodoItem> findByUserIdAndDueDateBetween( Long userId, LocalDateTime from, LocalDateTime dueDateBefore );
}
