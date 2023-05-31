package com.example.employeetaskmanagementsystem.service;

import com.example.employeetaskmanagementsystem.entity.Task;
import com.example.employeetaskmanagementsystem.entity.User;

import java.util.List;

public interface TaskService {

    List<Task> listAllTask();
    Task getTaskById(int id);
    Task insertTask(Task task);
    Task updateTask(Task task);
    int deleteTask(int id);
    List<Task> getTaskByAssignTo(User assignTo);
    List<Task> getTaskByAssignBy(User assignBy);
}
