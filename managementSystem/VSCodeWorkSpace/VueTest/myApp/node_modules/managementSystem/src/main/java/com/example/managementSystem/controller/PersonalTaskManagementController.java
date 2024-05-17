package com.example.managementSystem.controller;

import com.example.managementSystem.entity.Apply;
import com.example.managementSystem.entity.TaskReport;
import com.example.managementSystem.entity.vo.ApplicationWithAudit;
import com.example.managementSystem.entity.vo.TaskWithAppointorAndDepartmentAndAddress;
import com.example.managementSystem.service.PersonalTaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/personalTaskManagement")
public class PersonalTaskManagementController {

    @Autowired
    private PersonalTaskManagementService personalTaskManagementService;

    /**
     * 获取分派给我的任务
     * @param user_id
     * @return
     */
    @RequestMapping("/getMyNewTask")
    public Map<String,Object> getMyNewTask(@RequestParam(value = "user_id",required = false) Integer user_id){
        Map<String,Object>map =new HashMap<>();
        map.put("success",false);
        map.put("msg","数据获取失败");
        if (user_id==null) return map;
        List<TaskWithAppointorAndDepartmentAndAddress> tasks=new ArrayList<>();
        tasks = personalTaskManagementService.getMyNewTask(user_id);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",tasks);
        return map;
    }

    @RequestMapping("/signFor")
    public Map<String,Object> signFor(@RequestBody Integer[] assignmentIdList){
        Map<String,Object> map =new HashMap<>();
        map.put("success",false);
        map.put("msg","签收失败");
        if (assignmentIdList==null) return map;
        Integer count=0;
        count = personalTaskManagementService.signFor(assignmentIdList);
        if(count<assignmentIdList.length) return map;
        //成功
        map.put("success",true);
        map.put("msg","签收成功");
        return map;
    }

    /**
     * 获取需要申请的任务（已经审核但是未通过）
     * @param user_id
     * @return
     */
    @RequestMapping("/getMySignForedTask")
    public Map<String,Object>getMySignForedTask(@RequestParam(value = "user_id",required = false) Integer user_id){
        Map<String,Object>resultMap =new HashMap<>();
        resultMap.put("success",false);
        resultMap.put("msg","数据获取失败");
        if (user_id==null) return resultMap;
        Map<String,Object>map =new HashMap<>();
        map = personalTaskManagementService.getMySignForedTask(user_id);
        resultMap.put("success",true);
        resultMap.put("msg","获取成功");
        resultMap.put("data",map);
        return resultMap;
    }

    /**
     * 出差申请
     * @param apply
     * @return
     */
    @RequestMapping("/apply")
    public Map<String,Object> apply(@RequestBody Apply apply){
        Map<String,Object> map =new HashMap<>();
        map.put("success",false);
        map.put("msg","申请提交失败");
        //
        Integer count=0;
        count=personalTaskManagementService.apply(apply);
        if(count==0){
            return map;
        }

        if (count==3){
            map.put("success",false);
            map.put("msg","你对该任务的申请已经通过");
            return map;
        }

        map.put("success",true);
        map.put("msg","申请提交成功");
        return map;
    }

    /**
     * 我的申请记录
     * @param userId
     * @return
     */
    @RequestMapping("/getMyApplication")
    public Map<String,Object> getMyApplication(@RequestParam(value = "user_id",required = false)Integer userId){
        Map<String,Object> map =new HashMap<>();
        List<ApplicationWithAudit> applicationWithAuditList =new ArrayList<>();
        //
        if (userId==null){
            map.put("success",false);
            map.put("msg","缺少身份信息");
            return map;
        }
        applicationWithAuditList=personalTaskManagementService.getMyApplication(userId);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",applicationWithAuditList);
        return map;
    }

    @RequestMapping("/myOnGoingTask")
    public Map<String,Object> getMyOnGoingTask(@RequestParam(value = "user_id",required = false)Integer usreId){
        Map<String,Object> map =new HashMap<>();
        if (usreId==null){
            map.put("success",false);
            map.put("msg","缺少用户信息");
            return map;
        }
        List<TaskWithAppointorAndDepartmentAndAddress> taskList= personalTaskManagementService.getMyOnGoingTask(usreId);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",taskList);
        return map;
    }

    @RequestMapping("/myPastTask")
    public Map<String,Object> getMyPastTask(@RequestParam(value = "user_id",required = false)Integer usreId){
        Map<String,Object> map =new HashMap<>();
        if (usreId==null){
            map.put("success",false);
            map.put("msg","缺少用户信息");
            return map;
        }
        List<Map<String,Object>> mapList= personalTaskManagementService.getMyPastTask(usreId);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",mapList);
        return map;
    }

    @RequestMapping("/taskReport")
    public Map<String,Object> taskReport(@RequestBody TaskReport taskReport){
        Map<String,Object> map =new HashMap<>();
        map.put("success",false);
        map.put("msg","提交失败");
        //
        Integer count=0;
        count=personalTaskManagementService.taskReport(taskReport);
        if(count==0){
            return map;
        }
        map.put("success",true);
        map.put("msg","提交成功");
        return map;
    }
}
