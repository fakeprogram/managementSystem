package com.example.managementSystem.controller;


import com.example.managementSystem.entity.Apply;
import com.example.managementSystem.entity.AuditApplication;
import com.example.managementSystem.service.AuditService;
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
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    AuditService auditService;

    /**
     * 获取申请信息
     * @param userId
     * @return
     */
    @RequestMapping("/getUnAuditedApplication")
    public Map<String,Object> getUnAuditedApplication(@RequestParam(value = "user_id",required = false) Integer userId){
        Map<String,Object> map =new HashMap<>();
        List<Apply> applies =new ArrayList<>();
        if(userId==null){
            map.put("success",false);
            map.put("msg","缺少身份信息");
            return map;
        }
        //
        applies = auditService.getUnAuditedApplication(userId);
        map.put("success",true);
        map.put("msg","获取成功");
        map.put("data",applies);
        return map;
    }

    /**
     * 审核操作
     * @param auditApplication
     * @return
     */
    @RequestMapping("/auditOperation")
    public Map<String,Object> auditOperation(@RequestBody AuditApplication auditApplication){
        Map<String,Object> map=new HashMap<>();
        Integer count =0;
        count =auditService.auditOperation(auditApplication);
        if (count==-1){
            map.put("success",false);
            map.put("msg","该申请已经审核,无需再次审核");
            return map;
        }
        if(count==0){
            map.put("success",false);
            map.put("msg","提交失败");
        }
        map.put("success",true);
        map.put("msg","提交成功");
        return map;
    }
}
