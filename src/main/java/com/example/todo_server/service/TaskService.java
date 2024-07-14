package com.example.todo_server.service;

import com.example.todo_server.constants.TaskStatus;
import com.example.todo_server.model.Task;
import com.example.todo_server.persist.TaskRepository;
import com.example.todo_server.persist.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task add(String title, String description, LocalDate dueDate) {
        var task = TaskEntity.builder()
                .title(title)
                .description(description)
                .dueDate(Date.valueOf(dueDate))
                .status(TaskStatus.TODO)
                .build();
        var saved = this.taskRepository.save(task);

        return entityToObject(saved);
    }

    public List<Task> getAll() {
        return taskRepository.findAll().stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public List<Task> getByDueDate(String dueDate){
        return taskRepository.findAllByDueDate(Date.valueOf(dueDate)).stream()
                .map(this::entityToObject).collect(Collectors.toList());
    }
    public List<Task> getByStatus(TaskStatus status){
            return taskRepository.findAllByStatus(status).stream()
                    .map(this::entityToObject).collect(Collectors.toList());
    }

    public Task getOne(Long id){
        var entity = this.getById(id);
        return this.entityToObject(entity);
    }

    private TaskEntity getById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    private Task entityToObject(TaskEntity saved) {
        return Task.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .status(saved.getStatus())
                .dueDate(saved.getDueDate().toString())
                .createdAt(saved.getCreatedAt().toLocalDateTime())
                .updatedAt(saved.getUpdatedAt().toLocalDateTime())
                .build();
    }
}
