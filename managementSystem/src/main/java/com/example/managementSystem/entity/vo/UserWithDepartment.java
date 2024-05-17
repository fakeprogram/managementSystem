package com.example.managementSystem.entity.vo;

import com.example.managementSystem.entity.Users;
import lombok.Data;

@Data
public class UserWithDepartment extends Users {
    /**
     * 部门负责人
     */
    private Integer departmentHead;

    /**
     * 部门名称
     */
    private String departmentName;

    private static final long serialVersionUID = 1L;
}
