package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*") // adjust origin for production
public class TodoController {

  private final TodoRepository repo;
  public TodoController(TodoRepository repo) { this.repo = repo; }

  @GetMapping
  public List<Todo> all() { return repo.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Todo> get(@PathVariable Long id) {
    return repo.findById(id).map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Todo create(@RequestBody Todo todo) { return repo.save(todo); }

  @PutMapping("/{id}")
  public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody Todo body) {
    return repo.findById(id).map(t -> {
      t.setText(body.getText());
      t.setCompleted(body.isCompleted());
      return ResponseEntity.ok(repo.save(t));
    }).orElseGet(() -> {
      body.setId(id);
      return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(body));
    });
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Todo> partialUpdate(@PathVariable Long id, @RequestBody Todo body) {
    return repo.findById(id).map(t -> {
      if (body.getText() != null) t.setText(body.getText());
      if (body.isCompleted() != null) t.setCompleted(body.isCompleted());
      return ResponseEntity.ok(repo.save(t));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
