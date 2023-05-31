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
@RequestMapping("/user/task")
public class UserTaskController {
@Autowired
    private TaskService taskService;
@Autowired
private UserService userService;
@Autowired
private StatusService statusService;


    public UserTaskController(TaskService taskService, UserService userService, StatusService statusService) {
        this.taskService = taskService;
        this.userService = userService;
        this.statusService = statusService;
    }

    @GetMapping("/assignByMe")
    public String assignedTask(Model m,HttpSession session) {
        int uid = (int)session.getAttribute("uid");
        User user = userService.getUserById(uid);
        List<Task> list = taskService.getTaskByAssignBy(user);
        m.addAttribute("list", list);
        return "user/assigned";
    }
        @GetMapping("/myTask")
        public String myTask(Model m, HttpSession session) {
        int uid = (int)session.getAttribute("uid");
        User user = userService.getUserById(uid);
            List<Task> list = taskService.getTaskByAssignTo(user);
            m.addAttribute("list",list);
            return "user/mytask";
        }

    @GetMapping("/newassign")
    public String showForm(Task task,Model m) {

        List<User> list = userService.listAllUsers();
        m.addAttribute("listuser",list);
        List<Status> list1 = statusService.listAllStatus();
        m.addAttribute("liststatus",list1);

        return "user/task-entry";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("task")Task task, HttpSession session) {


        int uid = (int)session.getAttribute("uid");
        User user = userService.getUserById(uid);
        task.setAssignBy(user);

        task.setAssignDate(new Date());
        taskService.insertTask(task);

        return "redirect:/user/task/assignByMe";
    }

    @GetMapping(value= "/editassigned/{id}")
    public String edit(@PathVariable int id, Model m) {

        Task task = taskService.getTaskById(id);
        m.addAttribute("task",task);

        List<User> list = userService.listAllUsers();
        m.addAttribute("listuser",list);
        List<Status> list1 = statusService.listAllStatus();
        m.addAttribute("liststatus",list1);

        return "user/edit-assigned";
    }

    @PostMapping(value = "/updateassigned")
    public String update(@ModelAttribute("task")Task task , HttpSession session) {


        int uid = (int)session.getAttribute("uid");
        User user = userService.getUserById(uid);
        task.setAssignBy(user);


        task.setAssignDate(new Date());
        taskService.updateTask(task);
        return "redirect:/user/task/assignByMe";
    }



    @GetMapping(value ="/delete/{id}")
    public String delete(@PathVariable int id , Model m) {
        taskService.deleteTask(id);

        return "redirect:/user/task/assignByMe";
    }



    @GetMapping(value= "/editstatus/{id}")
    public String statusedit(@PathVariable int id, Model m) {

        Task task = taskService.getTaskById(id);
        m.addAttribute("task",task);

        List<Status> list1 = statusService.listAllStatus();
        m.addAttribute("liststatus",list1);

        return "user/editstatus";
    }

    @PostMapping(value = "/updatestatus")
    public String updatestatus(@RequestParam("status") String status,@RequestParam("id") int id, HttpSession session) {

        Task task1= taskService.getTaskById(id);
       // task1.setStatus(status);


        taskService.updateTask(task1);

        session.setAttribute("msg","Your task edited...");
        return "redirect:/user/task/myTask";
    }


}
