package com.example.employeetaskmanagementsystem.serviceimpl;

import com.example.employeetaskmanagementsystem.entity.Status;
import com.example.employeetaskmanagementsystem.repository.StatusRepository;
import com.example.employeetaskmanagementsystem.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    StatusRepository repository;
    @Override
    public List<Status> listAllStatus() {
        return repository.findAll();
    }

    @Override
    public Status getStatusById(int id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Status insertStatus(Status status) {
        return repository.save(status);
    }

    @Override
    public Status updateStatus(Status status) {
        return repository.save(status);
    }

    @Override
    public int deleteStatus(int id) {
        repository.deleteById(id);
        return id;
    }
}
