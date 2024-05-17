package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.Users;
import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.entity.vo.UserWithDepartmentAndAttendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Mapper
public interface UsersMapper {
    int deleteByPrimaryKey(Integer user_id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer user_id);

    Users selectByUserTel(String user_tel);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    //department.department_name as departmentName 取出的列名要和类中对应的属性名对应
    @Select("select users.* ,department.department_name as departmentName,department.department_head as departmentHead from users left join department on users.department_id=department.department_id where users.department_id=#{user_department_id}")
    List<UserWithDepartment> getStaffByDepartmentId(Integer user_department_id);

    @Select("select users.* ,department.department_name as departmentName,department.department_head as departmentHead from users left join department on users.department_id=department.department_id")
    List<UserWithDepartment> getAllStaff();

    @Select("select users.*,department.department_name as departmentName from users left join department on users.department_id=department.department_id where users.user_tel=#{userTel}")
    UserWithDepartment getUserWithDepartmentByTel(String userTel);

    @Select("select users.*,department.department_name as departmentName\n" +
            "   from users left join department on users.department_id=department.department_id\n" +
            "   left join task_assignments on users.user_id=task_assignments.receiver\n" +
            "   left join task on task_assignments.task_id=task.task_id\n" +
            "where task.task_id=#{taskId}")
    List<UserWithDepartment> getUsersByTaskId(Integer taskId);

    List<UserWithDepartmentAndAttendance> getOutSideStaffAttendanceOfToday(LocalDate date);

    List<UserWithDepartment> getAllOutSideStaff();
}