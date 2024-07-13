package com.example.todo_server.web.controller;

import com.example.todo_server.model.Task;
import com.example.todo_server.service.TaskService;
import com.example.todo_server.web.vo.TaskRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest req){
        var result = taskService.add(req.getTitle(), req.getDescription(), LocalDate.from(req.getDueDate()));
        return ResponseEntity.ok(result);
    }
}
