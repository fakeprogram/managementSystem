package com.example.managementSystem.service.Impl;

import com.example.managementSystem.entity.Attendance;
import com.example.managementSystem.entity.Users;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.mapper.AttendanceMapper;
import com.example.managementSystem.mapper.UsersMapper;
import com.example.managementSystem.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;



    @Override
    public Users getUserById(Integer user_id){
        return usersMapper.selectByPrimaryKey(user_id);
    }

    @Override
    public Users getUserByTel(String user_tel){
        return usersMapper.selectByUserTel(user_tel);
    }

    @Override
    public boolean login(String tel, String password){
        Users user =usersMapper.selectByUserTel(tel);
        if(user==null)return false;
        else  if(!user.getPass_word().equals(password)) return false;
        else return true;
    }



    /**
     *
     * @param user_tel
     * @return
     */
    @Override
    public UserWithDepartment getUserWithDepartmentByTel(String user_tel){
        return usersMapper.getUserWithDepartmentByTel(user_tel);
    }
}
