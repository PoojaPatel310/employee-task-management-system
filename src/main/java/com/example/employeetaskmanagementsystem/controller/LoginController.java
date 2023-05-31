package com.example.employeetaskmanagementsystem.controller;

import com.example.employeetaskmanagementsystem.entity.User;
import com.example.employeetaskmanagementsystem.service.UserService;
import com.example.employeetaskmanagementsystem.utility.Helper;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

@Controller

public class LoginController {

    @Autowired
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }



    @PostMapping("/login")
    public String checkLogin(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             HttpSession session)
    {
        User user = userService.findByEmailAndPassword(email, password);

        if (user != null) {
            session.setAttribute("uname", user.getFname() + "   " + user.getLname());
            session.setAttribute("fname", user.getFname());
            session.setAttribute("uid", user.getId());
            session.setAttribute("urole", user.getRole());
            session.setAttribute("email",user.getEmail());


            if (Helper.checkUserRole()) {
                session.setAttribute("msg", "You are successfully login..");
                return "redirect:/dashboard";

            } else {
                session.setAttribute("msg", "You are successfully login..");
                return "redirect:/dashboard";
            }
        }else
        {
            session.setAttribute("msg","Wrong Username or Password..");
            return "redirect:/login";
        }
    }
    @GetMapping("/dashboard")
    public String dashboard(){
        if (Helper.checkUserRole())
        {
            return "user/dashboard";
        }
        else {
            return "admin/dashboard";
        }
    }

    @GetMapping(value= "/profile")
    public String profile(Model m ,  HttpSession session){

        if(!Helper.checkUserRole() && !Helper.checkAdminRole()){
            return "redirect:/login ";
        }

        int uid=0;
        if(session.getAttribute("uid") != null) {
            uid = (int)session.getAttribute("uid");
        }
        User user = userService.getUserById(uid);
        m.addAttribute("user",user);

        if(Helper.checkUserRole()){
            return "user/profile";
        }
      else {
          return "admin/profile";
        }
    }


    @GetMapping(value= "/editprofile")
    public String editProfile(Model m, HttpSession session) {

        if(!Helper.checkUserRole() && !Helper.checkAdminRole()){
            return "redirect:/login ";
        }

        int uid=0;
        if(session.getAttribute("uid") != null) {
            uid = (int)session.getAttribute("uid");
        }
        User user = userService.getUserById(uid);
        m.addAttribute("user",user);

        if (Helper.checkUserRole()){
            return "user/edit-profile";
        }
        else {
            return "admin/edit-profile";
        }

    }

    @PostMapping(value = "/update-profile")
    public String updateProfile(@ModelAttribute("user")User user, HttpSession session) {

        User user2 =  userService.getUserById(user.getId());

        user2.setFname(user.getFname());
        user2.setLname(user.getLname());

        user2.setPhone(user.getPhone());

        userService.updateUser(user2);
        session.setAttribute("msg", "Your Profile is successfully updated..");
        return "redirect:/profile";
    }


    @GetMapping("/signup")
    public String signup(User user) {
        return "signup";
    }

    @PostMapping("/new-signup")
    public String newSignup(@ModelAttribute("user")User user,HttpSession session) {

        user.setRegDate(new Date());

        userService.insertUser(user);
        session.setAttribute("msg", "Sign up successfully!! Now you can login..");
        return "redirect:/login";
    }



    @GetMapping("/forgot-password")
    public String forgotPassword()

    {

        return "forgot-password";
    }

    @PostMapping("/update-forgot-password")
    public String updateForgotPassword(@RequestParam("email") String email ,
                                       HttpSession session, Model m) {

        User user = userService.findByEmail(email);
        if (user == null) {
            session.setAttribute("msg", "Email not found");
            return "redirect:/forgot-password";
        } else {
            String newVcode = Helper.generateRandomNumber();
            user.setVcode(newVcode);
            userService.updateUser(user);
            m.addAttribute("email",email);
            m.addAttribute("vcode",newVcode);
            return "verification";
        }
    }


    @PostMapping("/verificationCheck")
    public String verificationCheck(@RequestParam("email") String email,
                                    @RequestParam("vcode") String vcode,
                                @RequestParam("cvcode") String cvcode,Model m,
                                HttpSession session){
            if (vcode.equals(cvcode))
            {
                m.addAttribute("email",email);
                session.setAttribute("msg","verification code matched");
                return "reset-password";
            }
            else {
                session.setAttribute("msg", "Verification code did not match");
                return "redirect:/forgot-password";
            }

    }




    @PostMapping("/reset-password1")
    public String resetPassword(@RequestParam("password") String password,
                                @RequestParam("cpassword") String cpassword,
                                @RequestParam("email") String email,
                                HttpSession session){

        if(password.equals(cpassword)){

            User user = userService.findByEmail(email);
            user.setPassword(password);
            user = userService.updateUser(user);
            if (user!= null){
                session.setAttribute("msg","Password Reset");
                return "redirect:/login";
            }
            else {
                session.setAttribute("msg","Something went wrong!!");
                return "redirect:/reset-password";
            }
        }else {
            session.setAttribute("msg","Password and Confirm password not match");
            return "redirect:/reset-password";
        }

    }



    @GetMapping("/change-password")
    public String changePassword(HttpSession session) {
        if(!Helper.checkUserRole() && !Helper.checkAdminRole()){
            return "redirect:/login ";
        }
        if (Helper.checkUserRole()){
            return "user/change-password";
        }
        else {
            return "admin/change-password";
        }
    }

    @PostMapping("/update-change-password")
    public String updateChangePassword(@RequestParam("oldpassword") String oldpassword,
                                       @RequestParam("password") String password,
                                       @RequestParam("cpassword") String cpassword,
                                       HttpSession session){

        if(!Helper.checkUserRole() && !Helper.checkAdminRole()){
            return "redirect:/login ";
        }

        String email = session.getAttribute("email" ).toString();
        User user = userService.findByEmail(email);

        if (!user.getPassword().equals(oldpassword)){
            session.setAttribute("msg","Old Password is not matched");
            return "redirect:/change-password";
        }

        if(password.equals(cpassword)){

            user.setPassword(password);
            user = userService.updateUser(user);
            if (user!= null){
                session.setAttribute("msg","Password Reset Successfully");
                return "redirect:/profile";
            }
            else {
                session.setAttribute("msg","Something went wrong!!");
                return "redirect:/reset-password";
            }
        }else {
            session.setAttribute("msg","Password and Confirm password is not matched");
            return "redirect:/reset-password";
        }

    }

    @GetMapping("/logout")
    public String logout() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        if( session.getAttribute("uname") != null)
            session.removeAttribute("uname");

        if(session.getAttribute("uid") != null)
            session.removeAttribute("uid");
        if(session.getAttribute("urole") != null)
            session.removeAttribute("urole");

        session.setAttribute("msg","You are successfully logged-out from system..");
        return "redirect:/login";
    }




}
