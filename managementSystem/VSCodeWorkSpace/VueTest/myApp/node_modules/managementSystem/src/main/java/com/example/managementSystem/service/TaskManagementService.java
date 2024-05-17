package com.example.managementSystem.service;

import com.example.managementSystem.entity.Address;
import com.example.managementSystem.entity.Task;
import com.example.managementSystem.entity.Users;
import com.example.managementSystem.entity.vo.TaskWithAddressAndDepartment;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskManagementService {
    public int releaseTask(Task task,Integer user_id);//任务发布
    public List<Map<String,Object>> getPastTask(Integer userId);//获取历史任务
    public List<Map<String,Object>> getOnGoingTask(Integer userId);//获取正在进行的任务
    public List<UserWithDepartment> getStaff(Integer appointor);

    public int doAssignment(List<Integer> usersIdList, Integer task_id,Integer appointor);

    public Integer addAddress(Address address);

    public List<Map<String,Object>> getTaskReleasedByMe(Integer userId);
}
