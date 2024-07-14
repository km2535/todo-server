package com.example.todo_server.service;

import com.example.todo_server.constants.TaskStatus;
import com.example.todo_server.persist.TaskRepository;
import com.example.todo_server.persist.entity.TaskEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("할 일 추가 기능 테스트")
    void add() {
        var title = "test";
        var description = "test";
        var dueDate = LocalDate.now();

        when(taskRepository.save(any(TaskEntity.class))).thenAnswer(invocation ->{
            var e = (TaskEntity) invocation.getArguments()[0];
            e.setId(1L);
            e.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            e.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return e;
        });
        var actual = taskService.add(title, description, dueDate);

        verify(taskRepository, times(1)).save(any());
        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals(title, actual.getTitle());
        Assertions.assertEquals(description, actual.getDescription());
        Assertions.assertEquals(dueDate.toString(), actual.getDueDate());
        Assertions.assertEquals(TaskStatus.TODO, actual.getStatus());
        Assertions.assertNotNull(actual.getCreatedAt());
        Assertions.assertNotNull(actual.getUpdatedAt());

    }
}