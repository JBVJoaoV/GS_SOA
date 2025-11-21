package com.example.taskmanager.controller;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> create(@RequestBody Task task, Authentication auth) {
        UserDetails ud = (UserDetails) auth.getPrincipal();
        User owner = userRepository.findByUsername(ud.getUsername()).orElseThrow();
        task.setOwner(owner);
        Task saved = taskService.create(task);
        return ResponseEntity.ok(new ApiResponse<>(true, saved, "Created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> listMy(Authentication auth) {
        UserDetails ud = (UserDetails) auth.getPrincipal();
        User owner = userRepository.findByUsername(ud.getUsername()).orElseThrow();
        return ResponseEntity.ok(new ApiResponse<>(true, taskService.findByOwner(owner), "OK"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Task>>> listAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, taskService.findAll(), "OK"));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Task>> complete(@PathVariable Long id, Authentication auth) {
        Task t = taskService.findById(id).orElseThrow();
        UserDetails ud = (UserDetails) auth.getPrincipal();
        if (!t.getOwner().getUsername().equals(ud.getUsername()) && !auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, null, "Not allowed"));
        }
        t.setCompleted(true);
        Task saved = taskService.save(t);
        return ResponseEntity.ok(new ApiResponse<>(true, saved, "Completed"));
    }
}
