package com.example.todo_server.web.controller;

import com.example.todo_server.constants.TaskStatus;
import com.example.todo_server.model.Task;
import com.example.todo_server.service.TaskService;
import com.example.todo_server.web.vo.TaskRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("")
    public ResponseEntity<List<Task>> getTask(Optional<String> dueDate){
        List<Task> result;

        if(dueDate.isPresent()){
            result = this.taskService.getByDueDate(dueDate.get());
        }else{
            result = this.taskService.getAll();
        }
        return  ResponseEntity.ok(result);
    }

    /**
     *
     * @param id 할일 ID
     * @return ID에 해당하는 할일 객체
    */
    @GetMapping("/{id}")
    public ResponseEntity<Task> fetchOneTask(@PathVariable  Long id){
        var result = this.taskService.getOne(id);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 특정 상태에 해당하는 할일 목록을 반호나
     * 
     * @param status 할일 상태
     * @return 상태에 해당하는 할일 목록
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getByStatus(@PathVariable TaskStatus status){
        var result = this.taskService.getByStatus(status);
        return ResponseEntity.ok(result);
    }
}
