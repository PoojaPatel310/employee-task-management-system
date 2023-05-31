package com.example.employeetaskmanagementsystem.controller;

import com.example.employeetaskmanagementsystem.entity.Status;
import com.example.employeetaskmanagementsystem.entity.Task;
import com.example.employeetaskmanagementsystem.entity.User;
import com.example.employeetaskmanagementsystem.service.StatusService;
import com.example.employeetaskmanagementsystem.service.TaskService;
import com.example.employeetaskmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/task")
public class AdminTaskController {
    @Autowired
  private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatusService statusService;

    public AdminTaskController(TaskService taskService, UserService userService, StatusService statusService) {
        this.taskService = taskService;
        this.userService = userService;
        this.statusService = statusService;
    }

    @GetMapping("/list")
    public String listUser(Model m) {
        List<Task> list = taskService.listAllTask();
        m.addAttribute("list",list);
        return "admin/task-list";
    }


    @GetMapping("/new")
    public String showForm(Task task,Model m) {

        List<User> list = userService.listAllUsers();
        m.addAttribute("listuser",list);

        List<Status> list1 = statusService.listAllStatus();
        m.addAttribute("liststatus",list1);

        return "admin/task-entry";
    }


    @PostMapping("/insert")
    public String insert(@ModelAttribute("task")Task task, HttpSession session) {


        int uid = (int)session.getAttribute("uid");
        User user = userService.getUserById(uid);
        task.setAssignBy(user);

        task.setAssignDate(new Date());
        taskService.insertTask(task);

        return "redirect:/admin/task/list";
    }



    @GetMapping(value= "/edit/{id}")
    public String edit(@PathVariable int id, Model m) {

        Task task = taskService.getTaskById(id);
        m.addAttribute("task",task);



        return "admin/task-edit";
    }


    @PostMapping(value = "/update")
    public String update(@RequestParam ("id") int id ,
                             @RequestParam("title") String title,
                         @RequestParam("detail") String detail,
                         HttpSession session) {

        Task task = taskService.getTaskById(id);

        task.setTitle(title);
        task.setDetail(detail);

        taskService.updateTask(task);
        session.setAttribute("msg","Your task updated...");
        return "redirect:/admin/task/list";
    }

    @GetMapping(value ="/delete/{id}")
    public String delete(@PathVariable int id , Model m) {
       taskService.deleteTask(id);

        return "redirect:/admin/task/list";
    }

    @GetMapping("/assignByMe")
    public String assignedTask(Model m,HttpSession session) {
        int uid = (int)session.getAttribute("uid");
        User user = userService.getUserById(uid);
        List<Task> list = taskService.getTaskByAssignBy(user);
        m.addAttribute("list", list);
        return "admin/assigned";
    }


    @GetMapping("/myTask")
    public String myTask(Model m, HttpSession session) {
        int uid = (int)session.getAttribute("uid");
        User user = userService.getUserById(uid);
        List<Task> list = taskService.getTaskByAssignTo(user);
        m.addAttribute("list",list);
        return "admin/mytask";
    }


    @GetMapping(value= "/editstatus/{id}")
    public String statusedit(@PathVariable int id, Model m) {

        Task task = taskService.getTaskById(id);
        m.addAttribute("task",task);

        List<Status> list1 = statusService.listAllStatus();
        m.addAttribute("liststatus",list1);

        return "admin/editstatus";
    }


    @PostMapping(value = "/updatestatus")
    public String updatestatus(@RequestParam("status") int status_id,@RequestParam("id") int id, HttpSession session) {

        Status status = statusService.getStatusById(status_id);

        Task task = taskService.getTaskById(id);
        task.setStatus(status);

        taskService.updateTask(task);

        session.setAttribute("msg","Your task edited...");
        return "redirect:/admin/task/myTask";
    }

    @GetMapping(value= "/editassigned/{id}")
    public String editassigned(@PathVariable int id, Model m) {

        Task task = taskService.getTaskById(id);
        m.addAttribute("task",task);

        List<User> list = userService.listAllUsers();
        m.addAttribute("listuser",list);

        return "admin/edit-assigned";
    }

    @PostMapping(value = "/updateassigned")
    public String updateassigned(@ModelAttribute("task")Task task , HttpSession session) {

        int uid = (int)session.getAttribute("uid");
        User user = userService.getUserById(uid);
        task.setAssignBy(user);

        task.setAssignDate(new Date());
        taskService.updateTask(task);
        return "redirect:/admin/task/assignByMe";
    }

}
