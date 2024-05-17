package com.example.managementSystem.service;

import com.example.managementSystem.entity.Attendance;
import com.example.managementSystem.entity.Users;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    public Users getUserByTel(String user_tel);
    public boolean login(String tel, String password);
    public Users getUserById(Integer user_id);

    public UserWithDepartment getUserWithDepartmentByTel(String user_tel);

}
