package com.example.managementSystem.controller;

import com.example.managementSystem.entity.Address;
import com.example.managementSystem.entity.Task;
import com.example.managementSystem.entity.Users;
import com.example.managementSystem.entity.vo.TaskWithAddressAndDepartment;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.service.TaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/taskManagement")
public class TaskManagementController {
    @Autowired
    private TaskManagementService taskManagementService;

    @RequestMapping("/addAddress")
    public Map<String,Object> addAddress(@RequestBody Address address){
        Map<String,Object> map =new HashMap<>();
        Integer addressId=null;
        addressId = taskManagementService.addAddress(address);
        if(addressId==null){
            map.put("success",false);
            map.put("msg","地址添加失败");
            return map;
        }
        map.put("success",true);
        map.put("msg","添加成功");
        Map<String,Object> addMap=new HashMap<>();
        addMap.put("addressId",addressId);
        map.put("data",addMap);
        return map;
    }

    /**
     *
     * @param task
     * @param user_id
     * @return
     */
    @RequestMapping("/releaseTask")
    public Map<String,Object> releaseTask(@RequestBody Task task, @RequestParam(value = "user_id") Integer user_id){
        Map<String,Object> map =new HashMap<>();
        Integer newTaskId=taskManagementService.releaseTask(task,user_id);
        if(newTaskId==-1){
            map.put("success",false);
            map.put("msg","发布失败");
        }
        map.put("success",true);
        map.put("newTaskId",newTaskId);
        map.put("msg","发布成功");
        return map;
    }

    @RequestMapping("/getPastTask")
    public Map<String,Object> getPastTask(@RequestParam(value = "user_id" ,required = false) Integer user_id){
        Map<String,Object> map =new HashMap<>();
        if (user_id==null){
            map.put("success",false);
            map.put("msg","缺少用户信息");
            return map;
        }
        List<Map<String,Object>> mapList= taskManagementService.getPastTask(user_id);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",mapList);
        return map;
    }

    /**
     *
     * @param user_id
     * @return
     */
    @RequestMapping("/getOnGoingTask")
    public Map<String,Object> getOnGoingTask(@RequestParam(value = "user_id" ,required = false) Integer user_id){
        Map<String,Object> map =new HashMap<>();
        if (user_id==null){
            map.put("success",false);
            map.put("msg","缺少用户信息");
            return map;
        }
        List<Map<String,Object>> mapList= taskManagementService.getOnGoingTask(user_id);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",mapList);
        return map;
    }

    /**
     * 获取我发布的任务
     * @param userId
     * @return
     */
    @RequestMapping("/getTaskReleasedByMe")
    public Map<String,Object> getTaskReleasedByMe(@RequestParam(value = "user_id" ,required = false) Integer userId){
        Map<String,Object> map =new HashMap<>();
        if (userId==null){
            map.put("success",false);
            map.put("msg","缺少用户信息");
            return map;
        }
        List<Map<String,Object>> mapList= taskManagementService.getTaskReleasedByMe(userId);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",mapList);
        return map;
    }

    /*任务分派流程开始*/
    /**
     * 第一步，通过分派者ID获取可分派人员
     */
    /**
     *
     * @param appointor
     * @return
     */
    @RequestMapping("/assignment/getStaff")
    public Map<String,Object> getStaff(@RequestParam(value = "user_id",required = false) Integer appointor){
        Map<String,Object> map =new HashMap<>();
        if (appointor==null){
            map.put("success",false);
            map.put("msg","缺少用户信息,无法获取人员");
            return map;
        }
        List<UserWithDepartment> staff=taskManagementService.getStaff(appointor);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",staff);
        return map;
    }

    /**
     * 第二部，接收信息完成分派
     */
    /**
     *
     * @param usersIdList
     * @param task_id
     */
    @RequestMapping("/assignment/doAssignment")
    public Map<String,Object> doAssignment(@RequestBody List<Integer> usersIdList, @RequestParam(value = "task_id") Integer task_id, @RequestParam(value = "appointor") Integer appointor){
        Map<String,Object> map=new HashMap<>();
        Integer count=0;
        count= taskManagementService.doAssignment(usersIdList,task_id,appointor);
        if(count<usersIdList.size()){
            map.put("success",false);
            map.put("msg","分派失败");
            return map;
        }
        map.put("success",true);
        map.put("msg","分派成功");
        return map;
    }

    /**
     * 撤销分派
     */

    /*任务分派流程结束*/
}
