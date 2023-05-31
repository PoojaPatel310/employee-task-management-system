package com.example.employeetaskmanagementsystem.controller;

import com.example.employeetaskmanagementsystem.entity.Status;
import com.example.employeetaskmanagementsystem.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/list")
    public String listAll(Model m) {
        List<Status> list= statusService.listAllStatus();
        m.addAttribute("list",list);
        return "/status/list";
    }

    @GetMapping("/new")
    public String showForm(Status status) {
        return "/status/add";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("status") Status status) {
        statusService.insertStatus(status);
        return "redirect:/status/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model m) {
        Status status= statusService.getStatusById(id);
        m.addAttribute("status", status);
        return "/status/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ("status") Status status) {
        statusService.updateStatus(status);
        return "redirect:/status/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        statusService.deleteStatus(id);
        return "redirect:/status/list";
    }

}
