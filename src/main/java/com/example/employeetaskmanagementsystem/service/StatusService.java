package com.example.employeetaskmanagementsystem.service;

import com.example.employeetaskmanagementsystem.entity.Status;
import com.example.employeetaskmanagementsystem.entity.Task;

import java.util.List;

public interface StatusService {
    List<Status> listAllStatus();
    Status getStatusById(int id);
    Status insertStatus(Status status);
    Status updateStatus(Status status);
    int deleteStatus(int id);
}
