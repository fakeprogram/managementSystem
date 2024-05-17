package com.example.managementSystem.config;

import com.example.managementSystem.entity.Users;
import com.example.managementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorldController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    Users home(){
//        return "HelloWorld";
        return userService.getUserById(16);
    }

    @RequestMapping("/getUser")
    public Users getUser(@RequestParam("user_id") Integer user_id) {
        System.out.println(user_id);
        return userService.getUserById(user_id);
    }
}
