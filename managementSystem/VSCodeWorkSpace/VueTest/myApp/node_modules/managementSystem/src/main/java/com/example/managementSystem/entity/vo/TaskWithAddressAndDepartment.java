package com.example.managementSystem.entity.vo;

import com.example.managementSystem.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskWithAddressAndDepartment extends Task {
    /**
     * 地址名称
     */
    private String address_name;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区/县
     */
    private String county;

    /**
     * 街道/乡/镇
     */
    private String street;

    /**
     * 详细地址
     */
    private String last_detail;

    /**
     * 部门名称
     */
    private String departmentName;
}
