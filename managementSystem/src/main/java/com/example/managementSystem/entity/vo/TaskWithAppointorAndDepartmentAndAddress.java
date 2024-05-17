package com.example.managementSystem.entity.vo;

import com.example.managementSystem.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskWithAppointorAndDepartmentAndAddress extends Task {

    /**
     * 分派ID
     */
    private Integer assignmentId;

    /**
     * 分派时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assignmentDate;

    /**
     * 发布时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    /**
     * 分派人名称
     */
    private String appointor;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 地址
     */
    private String address;

}
