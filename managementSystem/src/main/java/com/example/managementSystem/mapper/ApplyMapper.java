package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.Apply;
import com.example.managementSystem.entity.vo.ApplicationWithAudit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ApplyMapper {
    int deleteByPrimaryKey(Integer applyId);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Integer applyId);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);

    int hasSuccessfulApplication(@Param("userId") Integer userId,@Param("taskId") Integer taskId);

    List<Apply> getUnAuditedApplicationByDepartmentId(Integer departmentId);

    List<Apply> getAllUnAuditedApplication();

    List<ApplicationWithAudit> getMyApplication(Integer userId);
}