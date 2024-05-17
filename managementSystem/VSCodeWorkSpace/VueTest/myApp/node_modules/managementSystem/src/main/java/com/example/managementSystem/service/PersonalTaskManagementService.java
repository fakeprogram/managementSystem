package com.example.managementSystem.service;

import com.example.managementSystem.entity.Apply;
import com.example.managementSystem.entity.TaskReport;
import com.example.managementSystem.entity.vo.ApplicationWithAudit;
import com.example.managementSystem.entity.vo.TaskWithAppointorAndDepartmentAndAddress;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PersonalTaskManagementService {
    //出差申请
    public int apply(Apply apply);

    public List<TaskWithAppointorAndDepartmentAndAddress> getMyNewTask(Integer user_id);

    public Integer signFor(Integer[] assignmentIdList);

     public List<ApplicationWithAudit> getMyApplication(Integer userId);

    public Map<String, Object> getMySignForedTask(Integer user_id);

    public List<TaskWithAppointorAndDepartmentAndAddress> getMyOnGoingTask(Integer usreId);

    public Integer taskReport(TaskReport taskReport);

    public List<Map<String,Object>> getMyPastTask(Integer usreId);
}
