package com.example.managementSystem.service.Impl;

import com.example.managementSystem.entity.*;
import com.example.managementSystem.entity.vo.TaskWithAddressAndDepartment;
import com.example.managementSystem.entity.vo.TaskWithAppointorAndDepartmentAndAddress;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.mapper.*;
import com.example.managementSystem.service.TaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskManagementServiceImpl implements TaskManagementService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ReleaseTaskMapper releaseTaskMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private TaskAssignmentsMapper taskAssignmentsMapper;

    @Autowired
    private AddressMapper addressMapper;

    public Integer addAddress(Address address) {
        Integer addressId = null;
        address.setAddressName(address.getProvince() + address.getCity() + address.getCounty() + address.getStreet() + address.getLastDetail());
        address.setAddTime(LocalDateTime.now());
        addressMapper.insert(address);
        addressId = address.getAddressId();
        return addressId;
    }

    /**
     * 发布任务
     *
     * @param task
     * @param user_id
     * @return
     */
    @Override
    public int releaseTask(Task task, Integer user_id) {
        Integer count = 0;
        count = taskMapper.insert(task);
        if (count == 1) {
            Integer newTaskId = task.getTaskId();
            ReleaseTask releaseTask = new ReleaseTask();
            releaseTask.setPublisher(user_id);
            releaseTask.setTaskId(newTaskId);
            releaseTask.setReleaseTime(LocalDateTime.now());
            count += releaseTaskMapper.insert(releaseTask);
            if (count == 2) return newTaskId;
            else return -1;
        }
        return -1;
    }



    /**
     * 获取历史任务
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getPastTask(Integer userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        List<TaskWithAddressAndDepartment> tasks = new ArrayList<>();
        if (user != null) {
            Integer role = user.getRole_type();
            if (role == 1) return null;
            else if (role == 2) {//二级用户获取本部门的历史任务
                tasks = taskMapper.getPastTaskByDepartmentId(user.getDepartment_id());
            } else {//role==3三级用户获取所有历史任务
                tasks = taskMapper.getAllPastTask();
            }

            //获取每个任务的负责人
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                Integer taskId = tasks.get(i).getTaskId();
                List<UserWithDepartment> users = new ArrayList<>();
                users = usersMapper.getUsersByTaskId(taskId);
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("task", tasks.get(i));
                tempMap.put("receivers", users);
                mapList.add(tempMap);
            }
            return mapList;
        }
        return null;
    }

    /**
     * 获取正在进行的任务
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getOnGoingTask(Integer userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        List<TaskWithAddressAndDepartment> tasks = new ArrayList<>();
        if (user != null) {
            Integer role = user.getRole_type();
            if (role == 1) return null;
            else if (role == 2) {//二级用户获取本部门的正在进行任务
                tasks = taskMapper.getOnGoingTaskByDepartmentId(user.getDepartment_id());
            } else {//role==3三级用户获取所有正在进行任务
                tasks = taskMapper.getAllOnGoingTask();
            }

            //获取每个任务的负责人
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                Integer taskId = tasks.get(i).getTaskId();
                List<UserWithDepartment> users = new ArrayList<>();
                users = usersMapper.getUsersByTaskId(taskId);
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("task", tasks.get(i));
                tempMap.put("receivers", users);
                mapList.add(tempMap);
            }
            return mapList;
        }
        return null;
    }

    /**
     * 获取我发布的任务
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getTaskReleasedByMe(Integer userId) {
        List<TaskWithAppointorAndDepartmentAndAddress> tasks = new ArrayList<>();
        tasks = taskMapper.getTaskReleasedByMe(userId);
        //获取每个任务的负责人
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Integer taskId = tasks.get(i).getTaskId();
            List<UserWithDepartment> users = new ArrayList<>();
            users = usersMapper.getUsersByTaskId(taskId);
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("task", tasks.get(i));
            tempMap.put("receivers", users);
            mapList.add(tempMap);
        }
        return mapList;
    }

    /**
     * 获取人员
     * @param appointor
     * @return
     */
    @Override
    public List<UserWithDepartment> getStaff(Integer appointor) {
        Users user = usersMapper.selectByPrimaryKey(appointor);//获取分派者信息
        if (user != null) {
            Integer role = user.getRole_type();
            if (role == 1) return null;
            else if (role == 2) {//二级用户获取本部门人员
                List<UserWithDepartment> staff = usersMapper.getStaffByDepartmentId(user.getDepartment_id());
                return staff;
            } else {//role==3三级用户获取所有正在进行任务
                List<UserWithDepartment> staff = usersMapper.getAllStaff();
                return staff;
            }
        }
        return null;
    }

    /**
     * 执行分派
     *
     * @param usersIdList
     * @param task_id
     */
    @Override
    public int doAssignment(List<Integer> usersIdList, Integer task_id, Integer appointor) {
        Integer count = 0;
        List<TaskAssignments> taskAssignmentsList = new ArrayList<>();
        for (int i = 0; i < usersIdList.size(); i++) {
            TaskAssignments taskAssignment = new TaskAssignments();
            taskAssignment.setAppointor(appointor);
            taskAssignment.setAssignmentStatus(0);
            taskAssignment.setTaskId(task_id);
            taskAssignment.setAssignmentDate(LocalDateTime.now());
            taskAssignment.setReceiver(usersIdList.get(i));
            taskAssignmentsList.add(taskAssignment);
        }
        count = taskAssignmentsMapper.insertBatch(taskAssignmentsList);
        return count;
    }
}
