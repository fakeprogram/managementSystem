package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.TaskAssignments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskAssignmentsMapper {
    int deleteByPrimaryKey(Integer assignmentId);

    int insert(TaskAssignments record);

    int insertSelective(TaskAssignments record);

    TaskAssignments selectByPrimaryKey(Integer assignmentId);

    int updateByPrimaryKeySelective(TaskAssignments record);

    int updateByPrimaryKey(TaskAssignments record);

    /**
     * 批量新增数据
     *
     * @param entities List<TaskAssignments> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TaskAssignments> entities);
}