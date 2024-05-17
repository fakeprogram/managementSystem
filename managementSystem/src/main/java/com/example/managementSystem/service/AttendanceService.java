package com.example.managementSystem.service;

import com.example.managementSystem.entity.Attendance;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.entity.vo.UserWithDepartmentAndAttendance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {
    public Map<String,Object> clockIn(Attendance attendance);
    public List<Attendance> getMyAttendance(Integer userId);

    public List<UserWithDepartmentAndAttendance> getOutSideStaffAttendanceOfToday();

    public Integer endMyBusinessTrip(Integer userId);
}
