package com.example.managementSystem.controller;

import com.example.managementSystem.entity.Attendance;
import com.example.managementSystem.entity.vo.UserWithDepartmentAndAttendance;
import com.example.managementSystem.service.AttendanceService;
import com.example.managementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     *
     * @param attendance HTML5获取经纬度（GPS），百度地图API转为百度经纬度，百度地址解析为地址
     * @return Map<String ,Object>
     */
    @RequestMapping("/clockIn")
    public Map<String ,Object> clockIn(@RequestBody Attendance attendance){
        Map<String,Object> map=new HashMap<>();
        map.put("success",false);
        map.put("msg","打卡失败");
        if (attendance.getCity()==null ||attendance.getLat()==null ||attendance.getLng()==null ||attendance.getUserId()==null){
            return map;
        }
        attendance.setDate(LocalDateTime.now());
        Map<String,Object> tempMap=new HashMap<>();
        tempMap.put("success",false);
        tempMap=attendanceService.clockIn(attendance);
        boolean ok = (boolean) tempMap.get("success");
        if(ok){
            return tempMap;
        }
        return map;
    }

    @RequestMapping("/endMyBusinessTrip")
    public Map<String,Object> endMyBusinessTrip(@RequestParam(value = "user_id",required = false)Integer userId){
        Map<String,Object> map =new HashMap<>();
        if (userId==null){
            map.put("success",false);
            map.put("msg","缺少用户信息");
            return map;
        }
        Integer count=0;
        count=attendanceService.endMyBusinessTrip(userId);
        if (count!=0 ||count!=null){
            map.put("success",true);
            map.put("msg","操作成功");
            return map;
        }
        map.put("success",false);
        map.put("msg","操作失败");
        return map;
    }

    /**
     * 获取我的打卡记录
     */
    @RequestMapping("/getMyAttendance")
    public Map<String,Object> getMyAttendance(@RequestParam(value = "user_id",required = false)Integer userId){
        Map<String,Object> map =new HashMap<>();
        if (userId==null){
            map.put("success",false);
            map.put("msg","缺少用户信息");
            return map;
        }
        List<Attendance> attendances =new ArrayList<>();
        attendances=attendanceService.getMyAttendance(userId);
        map.put("success",true);
        map.put("msg","获取考勤记录成功");
        map.put("data",attendances);
        return map;
    }

    /**
     * 获取已打卡在外员工今日考勤数据()
     * @return
     */
    @RequestMapping("/getOutSideStaffAttendanceOfToday")
    public Map<String,Object> getOutSideStaffAttendanceOfToday(){
        Map<String,Object> map =new HashMap<>();
        List<UserWithDepartmentAndAttendance> userWithDepartmentAndAttendances =new ArrayList<>();
        userWithDepartmentAndAttendances=attendanceService.getOutSideStaffAttendanceOfToday();
        map.put("success",true);
        map.put("msg","获取考勤数据成功");
        map.put("data",userWithDepartmentAndAttendances);
        return map;
    }
}
