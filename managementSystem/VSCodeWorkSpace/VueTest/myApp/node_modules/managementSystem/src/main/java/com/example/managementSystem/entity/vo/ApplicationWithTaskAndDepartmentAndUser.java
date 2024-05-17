package com.example.managementSystem.entity.vo;

import com.example.managementSystem.entity.Apply;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationWithTaskAndDepartmentAndUser extends Apply {

/**
 * 申请信息全部
 * 任务id+任务名称+任务地点
 * 申请人名称
 * 申请人所属部门名称
 */

    /**
     * 申请人名称
     */
    private String userName;

    /**
     * 申请人部门
     */
    private String departmentName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务地址
     */
    private String addressName;
}
