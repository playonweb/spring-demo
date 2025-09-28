package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "todos")
public class Todo {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;
  private Boolean completed;

  public Todo() {}
  public Todo(String text, Boolean completed) { this.text = text; this.completed = completed; }

  // getters / setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getText() { return text; }
  public void setText(String text) { this.text = text; }
  public Boolean isCompleted() { return completed; }
  public void setCompleted(Boolean completed) { this.completed = completed; }
}
