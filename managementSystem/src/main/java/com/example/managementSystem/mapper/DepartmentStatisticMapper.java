package com.example.managementSystem.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface DepartmentStatisticMapper {

    @Select("select\n" +
            "  sum(apply.days) as allDays,\n" +
            "  users.user_name\n" +
            "from users\n" +
            "  left join department on users.department_id = department.department_id\n" +
            "  left join apply on apply.user_id = users.user_id\n" +
            "  left join audit_application on apply.apply_id = audit_application.apply_id\n" +
            "where audit_application.is_pass = 1 and apply.departure_time like CONCAT(#{year},'%')\n" +
            "group by apply.user_id\n" +
            "order by days DESC")
    List<Map<String, Object>> getPersonalBusinessTravelTimeRankings(int year);

    @Select("select\n" +
            "  count(*) as num\n" +
            "from task\n" +
            "where task_status=2")
    Integer getNumOfTasks();

    @Select("select\n" +
            "  city,\n" +
            "  count(attendance.user_id) as num\n" +
            "from attendance left join users on attendance.user_id=users.user_id\n" +
            "where date like CONCAT(#{date},'%') and users.user_status=1\n" +
            "group by city\n" +
            "order by num")
    List<Map<String, Object>> getDistributionOfExpatriates(LocalDate date);

    @Select("select\n" +
            "  sum(transportation_expense) as \"transportation_expense\",\n" +
            "  sum(hotel_expense) as \"hotel_expense\",\n" +
            "  sum(catering_expenses) as \"catering_expenses\",\n" +
            "  sum(other_expenses) as \"other_expenses\"\n" +
            "from apply left join audit_application on audit_application.apply_id=apply.apply_id\n" +
            "  left join users on apply.user_id=users.user_id\n" +
            "  left join department on users.department_id=department.department_id\n" +
            "where audit_application.is_pass=1 and users.department_id=#{departmentId} and DATE_FORMAT(apply.apply_time,'%Y%m') = DATE_FORMAT(CURDATE(),'%Y%m')")
    Map<String, Object> getProportionOfDepartmentalExpenditureTypes(Integer departmentId);

    @Select("select\n" +
            "  sum(total_cost) as total_cost,DATE_FORMAT(apply.apply_time,'%M') as month\n" +
            "from apply left join audit_application on audit_application.apply_id=apply.apply_id\n" +
            "  left join users on apply.user_id=users.user_id\n" +
            "  left join department on users.department_id=department.department_id\n" +
            "where audit_application.is_pass=1 and users.department_id=#{departmentId}\n" +
            "group by month")
    List<Map<String,Object>> getDepartmentalAnnualExpenditure(Integer departmentId);

    @Select("select\n" +
            "  department_name,\n" +
            "  sum(total_cost) as cost,\n" +
            "  DATE_FORMAT(apply_time,\"%Y-%m\") as month\n" +
            "from apply left join audit_application on apply.apply_id=audit_application.apply_id\n" +
            "where DATE_FORMAT(apply_time,\"%Y-%m\")=DATE_FORMAT(CURDATE(),\"%Y-%m\") and audit_application.is_pass=1\n" +
            "group by department_name")
    List<Map<String,Object>> getMonthlyExpenditureOfEachDepartment();

    @ Select("select\n" +
            "  vehicle,\n" +
            "  count(vehicle) as value\n" +
            "from apply left join audit_application on audit_application.apply_id=apply.apply_id\n" +
            "  left join users on apply.user_id=users.user_id\n" +
            "  left join department on users.department_id=department.department_id\n" +
            "where audit_application.is_pass=1 and users.department_id=#{departmentId} and DATE_FORMAT(apply.apply_time,'%Y%m') = DATE_FORMAT(CURDATE(),'%Y%m')\n" +
            "group by vehicle")
    List<Map<String,Object>> getProportionOfTransportationModesInDepartments(Integer departmentId);
}
