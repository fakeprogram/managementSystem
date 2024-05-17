package com.example.managementSystem.service.Impl;

import com.example.managementSystem.entity.*;
import com.example.managementSystem.entity.vo.ApplicationWithAudit;
import com.example.managementSystem.entity.vo.TaskWithAddressAndDepartment;
import com.example.managementSystem.entity.vo.TaskWithAppointorAndDepartmentAndAddress;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.mapper.*;
import com.example.managementSystem.service.PersonalTaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonalTaskManagementServiceImpl implements PersonalTaskManagementService {

    @Autowired
    private PersonalTaskManagementMapper personalTaskManagementMapper;

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private TaskAssignmentsMapper taskAssignmentsMapper;

    @Autowired
    private TaskReportMapper taskReportMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UsersMapper usersMapper;


    /**
     * 获取新任务
     *
     * @param user_id
     * @return
     */
    public List<TaskWithAppointorAndDepartmentAndAddress> getMyNewTask(Integer user_id) {
        List<TaskWithAppointorAndDepartmentAndAddress> tasks = new ArrayList<>();
        //
        tasks = personalTaskManagementMapper.selectNewTaskByUserId(user_id);
        return tasks;
    }

    /**
     * 获取已签收任务用于填写申请
     *
     * @param user_id
     * @return
     */
    public Map<String, Object> getMySignForedTask(Integer user_id) {
        List<TaskWithAppointorAndDepartmentAndAddress> tempTasks = new ArrayList<>();
        List<TaskWithAppointorAndDepartmentAndAddress> tasks = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        tempTasks = personalTaskManagementMapper.selectSignForedTaskByUserId(user_id);
        map.put("allTask", tempTasks);
        for (int i = 0; i < tempTasks.size(); i++) {//出去已经通过申请的任务
            if (applyMapper.hasSuccessfulApplication(user_id, tempTasks.get(i).getTaskId()) != 0) {
                continue;
            }
            tasks.add(tempTasks.get(i));
        }
        map.put("taskWithoutApplication", tasks);
        return map;
    }

    /**
     * 签收任务
     *
     * @param assignmentIdList
     * @return
     */
    public Integer signFor(Integer[] assignmentIdList) {
        Integer update = 0;
        for (int i = 0; i < assignmentIdList.length; i++) {
            TaskAssignments updatedTaskAssignment = new TaskAssignments();
            Integer assignmentId = assignmentIdList[i];
            updatedTaskAssignment.setAssignmentId(assignmentId);
            updatedTaskAssignment.setSignForTime(LocalDateTime.now());
            updatedTaskAssignment.setAssignmentStatus(1);
            update += taskAssignmentsMapper.updateByPrimaryKeySelective(updatedTaskAssignment);
        }
        return update;
    }

    /**
     * 提交申请
     *
     * @param apply
     * @return
     */
    @Override
    public int apply(Apply apply) {
        Integer count = 0;
        Integer tempUserId = apply.getUserId();
        Integer tempTaskId = apply.getTaskId();
        apply.setApplyTime(LocalDateTime.now());
        if (applyMapper.hasSuccessfulApplication(tempUserId, tempTaskId) != 0) {//已经有成功的申请
            return 3;
        }
        count = applyMapper.insert(apply);
        return count;
    }

    /**
     * 获取申请记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<ApplicationWithAudit> getMyApplication(Integer userId) {
        List<ApplicationWithAudit> applicationWithAudit = new ArrayList<>();
        //
        applicationWithAudit = applyMapper.getMyApplication(userId);
        return applicationWithAudit;
    }

    /**
     * 获取我的正在进行的任务
     *
     * @param usreId
     * @return
     */
    public List<TaskWithAppointorAndDepartmentAndAddress> getMyOnGoingTask(Integer usreId) {
        List<TaskWithAppointorAndDepartmentAndAddress> tasks = new ArrayList<>();
        tasks = personalTaskManagementMapper.getMyOnGoingTask(usreId);
        return tasks;
    }

    /**
     * 我的历史任务
     *
     * @param usreId
     * @return
     */
    public List<Map<String, Object>> getMyPastTask(Integer usreId) {
        List<TaskWithAddressAndDepartment> tasks = new ArrayList<>();
        tasks = taskMapper.getMyPastTask(usreId);
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
     * 任务汇报
     *
     * @param taskReport
     * @return
     */
    public Integer taskReport(TaskReport taskReport) {
        Integer count = 0;
        Integer tempTaskId = taskReport.getTaskId();
        Task updateTask = new Task();
        updateTask.setTaskId(tempTaskId);
        updateTask.setTaskProgress(taskReport.getTaskprogress());
        taskReport.setReportTime(LocalDateTime.now());
        count += taskReportMapper.insert(taskReport);
        count += taskMapper.updateByPrimaryKeySelective(updateTask);
        if (count != 2) {
            return 0;
        }
        return 1;
    }
}
