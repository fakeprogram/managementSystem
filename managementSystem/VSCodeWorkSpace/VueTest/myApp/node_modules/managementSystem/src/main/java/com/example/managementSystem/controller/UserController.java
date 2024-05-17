package com.example.managementSystem.controller;

import com.auth0.jwt.JWT;
import com.example.managementSystem.entity.Attendance;
import com.example.managementSystem.entity.Users;
import com.example.managementSystem.service.UserService;
import com.example.managementSystem.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
    public Map<String, Object> login(@RequestBody Users user){
        Map<String, Object> map = new HashMap<>();
        // 账号密码验证
        if(userService.login(user.getUser_tel(),user.getPass_word())){
            Users tempUser=userService.getUserWithDepartmentByTel(user.getUser_tel());
            tempUser.setPass_word("******");
            // 验证成功后发送token
            String token = JWTUtil.getToken(tempUser.getUser_id(),user.getPass_word());
            if (token != null){
                map.put("success", true);
                map.put("msg","认证成功");
                map.put("token", token);
                map.put("user",tempUser);
                return map;
            }
        }
        map.put("success", false);
        map.put("msg","用户名或密码错误");
        return map;
    }
}
