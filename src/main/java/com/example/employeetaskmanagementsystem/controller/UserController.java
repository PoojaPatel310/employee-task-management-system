package com.example.employeetaskmanagementsystem.controller;

import com.example.employeetaskmanagementsystem.entity.User;
import com.example.employeetaskmanagementsystem.service.UserService;
import com.example.employeetaskmanagementsystem.utility.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listUser(Model m) {
        List<User> list = userService.listAllUsers();
        m.addAttribute("list",list);
        return "admin/user-list";
    }


    @GetMapping("/new")
    public String showForm(User user) {

        return "/admin/user-entry";
    }






    @PostMapping("/insert")
    public String insertUser(@ModelAttribute("user")User user) {

        user.setRegDate(new Date());

        String verificationCode = Helper.generateRandomNumber();
        user.setVcode(String.valueOf(verificationCode));

        userService.insertUser(user);
        return "redirect:/admin/user/list";
    }

    @GetMapping(value= "/edit/{id}")
    public String edit(@PathVariable int id, Model m) {

        User user = userService.getUserById(id);
        m.addAttribute("user",user);

        return "/admin/user-edit";
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user")User user) {

        user.setRegDate(new Date());

        String verificationCode = Helper.generateRandomNumber();
        user.setVcode(String.valueOf(verificationCode));

        userService.updateUser(user);
        return "redirect:/admin/user/list";
    }

    @GetMapping(value ="/delete/{id}")
    public String deleteUser(@PathVariable int id , Model m) {
        userService.deleteUser(id);
        return "redirect:/admin/user/list";
    }


}
