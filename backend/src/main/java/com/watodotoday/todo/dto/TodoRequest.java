package com.watodotoday.todo.dto;

import com.watodotoday.domain.todo.Priority;

import java.time.LocalDateTime;

public record TodoRequest(String title, String description, Priority priority, LocalDateTime dueDate) {

}
