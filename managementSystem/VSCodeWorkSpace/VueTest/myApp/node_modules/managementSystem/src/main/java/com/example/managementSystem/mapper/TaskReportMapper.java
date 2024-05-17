package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.TaskReport;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(TaskReport record);

    int insertSelective(TaskReport record);

    TaskReport selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(TaskReport record);

    int updateByPrimaryKey(TaskReport record);
}