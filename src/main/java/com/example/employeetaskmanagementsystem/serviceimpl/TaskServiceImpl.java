package com.example.employeetaskmanagementsystem.serviceimpl;

import com.example.employeetaskmanagementsystem.entity.Task;
import com.example.employeetaskmanagementsystem.entity.User;
import com.example.employeetaskmanagementsystem.repository.TaskRepository;
import com.example.employeetaskmanagementsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
TaskRepository repository;
    @Override
    public List<Task> listAllTask() {
        return repository.findAll();
    }

    @Override
    public Task getTaskById(int id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Task insertTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return repository.save(task);
    }


    @Override
    public int deleteTask(int id) {
        repository.deleteById(id);
        return id;
    }

    @Override
    public List<Task> getTaskByAssignTo(User assignTo) {
        return repository.findByAssignTo(assignTo);
    }

    @Override
    public List<Task> getTaskByAssignBy(User assignBy)
    {
        return repository.findByAssignBy(assignBy);
    }


}
