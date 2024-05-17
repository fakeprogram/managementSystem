package com.example.managementSystem.service.Impl;

import com.example.managementSystem.entity.Attendance;
import com.example.managementSystem.entity.Users;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.entity.vo.UserWithDepartmentAndAttendance;
import com.example.managementSystem.mapper.AttendanceMapper;
import com.example.managementSystem.mapper.UsersMapper;
import com.example.managementSystem.service.AttendanceService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    UsersMapper usersMapper;

    /**
     * 打卡
     * @param attendance
     * @return
     */
    @Override
    public Map<String ,Object> clockIn(Attendance attendance){
        Integer count=0;
        Map<String ,Object> map =new HashMap<>();
        attendance.setDate(LocalDateTime.now());
        count=attendanceMapper.insert(attendance);
        if(count!=0){//成功
            map.put("success",true);
            map.put("msg",attendance.getCity());
            return map;
        }
        map.put("success",false);
        map.put("msg","打卡数据存储失败");
        return map;
    }

    /**
     *结束出差
     */
    public Integer endMyBusinessTrip(Integer userId){
        Integer count=0;
        Users user=new Users();
        user.setUser_status(0);
        user.setUser_id(userId);
        count=usersMapper.updateByPrimaryKeySelective(user);
        return count;
    }

    /**
     * 获取我的所有打卡记录
     * @param userId
     * @return
     */
    @Override
    public List<Attendance> getMyAttendance(Integer userId){
        List<Attendance>attendances=new ArrayList<>();
        attendances=attendanceMapper.selectByUserId(userId);
        return  attendances;
    }

    /**
     * 获取今日已打卡人员
     * @return
     */
    @Override
    public List<UserWithDepartmentAndAttendance> getOutSideStaffAttendanceOfToday(){
        List<UserWithDepartmentAndAttendance>userWithDepartmentAndAttendances =new ArrayList<>();
        LocalDate date = LocalDate.now();
        userWithDepartmentAndAttendances=usersMapper.getOutSideStaffAttendanceOfToday(date);
        return userWithDepartmentAndAttendances;
    }


}
