package com.LUCUX.XPlanner.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.LUCUX.XPlanner.model.Todo;


public interface TodoRepository extends JpaRepository<Todo, Long> {
}
