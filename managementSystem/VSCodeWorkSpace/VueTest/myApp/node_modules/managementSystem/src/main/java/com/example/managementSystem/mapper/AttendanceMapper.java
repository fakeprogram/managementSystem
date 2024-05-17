package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.Attendance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(Attendance record);

    int insertSelective(Attendance record);

    Attendance selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(Attendance record);

    int updateByPrimaryKey(Attendance record);

    List<Attendance> selectByUserId(Integer userId);
}