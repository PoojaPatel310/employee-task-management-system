package com.example.employeetaskmanagementsystem.repository;

import com.example.employeetaskmanagementsystem.entity.Task;
import com.example.employeetaskmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {

        List<Task> findByAssignTo(User assignTo);
        List<Task> findByAssignBy(User assignBy);
}
