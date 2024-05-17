package com.example.managementSystem.service.Impl;

import com.example.managementSystem.entity.Apply;
import com.example.managementSystem.entity.AuditApplication;
import com.example.managementSystem.entity.Task;
import com.example.managementSystem.entity.Users;
import com.example.managementSystem.mapper.ApplyMapper;
import com.example.managementSystem.mapper.AuditApplicationMapper;
import com.example.managementSystem.mapper.TaskMapper;
import com.example.managementSystem.mapper.UsersMapper;
import com.example.managementSystem.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private AuditApplicationMapper auditApplicationMapper;

    @Autowired
    private TaskMapper taskMapper;

    /**
     *获取申请
     * @param userId
     * @return
     */
    @Override
    public List<Apply> getUnAuditedApplication(Integer userId){
        Users user=usersMapper.selectByPrimaryKey(userId);//获取审核者信息
        if(user!=null){
            Integer role=user.getRole_type();
            List<Apply> applies =new ArrayList<>();
            if(role==1) return null;
            else if(role==2){//二级用户获取本部门申请
                applies=applyMapper.getUnAuditedApplicationByDepartmentId(user.getDepartment_id());
                return  applies;
            }
            else {//role==3三级用户获取所有申请
                applies=applyMapper.getAllUnAuditedApplication();
                return applies;
            }
        }
        return null;
    }

    /**
     * 执行审批
     */
    @Override
    public Integer auditOperation(AuditApplication auditApplication){
        Integer audited=0;
        audited =auditApplicationMapper.countByApplyId(auditApplication.getApplyId());
        if (audited!=0) return -1;//已经审核过该申请
        auditApplication.setAuditTime(LocalDateTime.now());
        Integer count=0;
        count=auditApplicationMapper.insert(auditApplication);
        if(count!=0 &&auditApplication.getIsPass()==1){//通过申请
            //设置申请状态为已经审核
            Integer applyId=auditApplication.getApplyId();
            Apply newApply=applyMapper.selectByPrimaryKey(applyId);
            newApply.setApplyStatus(1);
            applyMapper.updateByPrimaryKeySelective(newApply);

            //设置任务状态为进行中
            Integer taskId=newApply.getTaskId();
            Task newTask = taskMapper.selectByPrimaryKey(taskId);
            newTask.setTaskStatus(2);
            taskMapper.updateByPrimaryKeySelective(newTask);

            // 设置申请者为出差状态
            Integer userId =newApply.getUserId();
            Users newUser = usersMapper.selectByPrimaryKey(userId);
            newUser.setUser_status(1);
            usersMapper.updateByPrimaryKeySelective(newUser);
        }
        else if(count!=0 &&auditApplication.getIsPass()==0){//不通过申请
            //设置申请状态为已经审核
            Integer applyId=auditApplication.getApplyId();
            Apply newApply=applyMapper.selectByPrimaryKey(applyId);
            newApply.setApplyStatus(1);
            applyMapper.updateByPrimaryKeySelective(newApply);
        }
        return count;
    }
}

