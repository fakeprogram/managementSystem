package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.vo.TaskWithAddressAndDepartment;
import com.example.managementSystem.entity.vo.TaskWithAppointorAndDepartmentAndAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PersonalTaskManagementMapper {

    /**
     *
     * @param userId
     * @return
     */
    @Select("select\n" +
            "  a.task_id         as taskId,\n" +
            "  a.task_name       as taskName,\n" +
            "  a.department_id   as departmentId,\n" +
            "  a.address_id      as addressId,\n" +
            "  a.start_time      as startTime,\n" +
            "  a.end_time        as endTime,\n" +
            "  a.task_detail     as taskDetail,\n" +
            "  a.task_file       as taskFile,\n" +
            "  a.task_progress   as taskProgress,\n" +
            "  a.task_type       as taskType,\n" +
            "  a.task_status     as taskStatus,\n" +
            "  e.address_name    as address,\n" +
            "  b.assignment_date as assignmentDate,\n" +
            "  b.assignment_id   as assignmentId,\n" +
            "  c.user_name       as appointor,\n" +
            "  d.department_name as departmentName\n" +
            "from task a left join task_assignments b on a.task_id = b.task_id\n" +
            "  left join users c on b.appointor = c.user_id\n" +
            "  left join department d on a.department_id = d.department_id\n" +
            "  left join address e on a.address_id = e.address_id\n" +
            "where b.receiver = #{userId} and b.assignment_status = 0")
    List<TaskWithAppointorAndDepartmentAndAddress> selectNewTaskByUserId(Integer userId);

    @Select("select DISTINCT\n" +
            "  a.task_id         as taskId,\n" +
            "  a.task_name       as taskName,\n" +
            "  a.department_id   as departmentId,\n" +
            "  a.address_id      as addressId,\n" +
            "  a.start_time      as startTime,\n" +
            "  a.end_time        as endTime,\n" +
            "  a.task_detail     as taskDetail,\n" +
            "  a.task_file       as taskFile,\n" +
            "  a.task_progress   as taskProgress,\n" +
            "  a.task_type       as taskType,\n" +
            "  a.task_status     as taskStatus,\n" +
            "  e.address_name    as address,\n" +
            "  b.assignment_date as assignmentDate,\n" +
            "  b.assignment_id   as assignmentId,\n" +
            "  c.user_name       as appointor,\n" +
            "  d.department_name as departmentName\n" +
            "from task as a\n" +
            "  left join task_assignments as b on a.task_id = b.task_id\n" +
            "  left join users as c on b.appointor = c.user_id\n" +
            "  left join department as d on a.department_id = d.department_id\n" +
            "  left join address as e on a.address_id = e.address_id\n" +
            "where b.receiver = #{userId} and b.assignment_status = 1 ORDER BY b.sign_for_time DESC ")
    List<TaskWithAppointorAndDepartmentAndAddress> selectSignForedTaskByUserId(Integer userId);

    @Select("select\n" +
            "  a.task_id         as taskId,\n" +
            "  a.task_name       as taskName,\n" +
            "  a.department_id   as departmentId,\n" +
            "  a.address_id      as addressId,\n" +
            "  a.start_time      as startTime,\n" +
            "  a.end_time        as endTime,\n" +
            "  a.task_detail     as taskDetail,\n" +
            "  a.task_file       as taskFile,\n" +
            "  a.task_progress   as taskProgress,\n" +
            "  a.task_type       as taskType,\n" +
            "  a.task_status     as taskStatus,\n" +
            "  e.address_name    as address,\n" +
            "  b.assignment_date as assignmentDate,\n" +
            "  b.assignment_id   as assignmentId,\n" +
            "  c.user_name       as appointor,\n" +
            "  d.department_name as departmentName,\n" +
            "  f.release_time as releaseTime\n" +
            "from task a left join task_assignments b on a.task_id = b.task_id\n" +
            "  left join users c on b.appointor = c.user_id\n" +
            "  left join department d on a.department_id = d.department_id\n" +
            "  left join address e on a.address_id = e.address_id\n" +
            "  left join release_task f on a.task_id =f.task_id\n" +
            "where b.receiver=#{userId} and  a.task_status=2\n" +
            "ORDER BY f.release_time DESC;")
    List<TaskWithAppointorAndDepartmentAndAddress> getMyOnGoingTask(Integer usreId);


    List<TaskWithAddressAndDepartment> getMyPastTask(Integer usreId);
}
