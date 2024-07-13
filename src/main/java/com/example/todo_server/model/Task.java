package com.example.todo_server.model;

import com.example.todo_server.constants.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private String dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
