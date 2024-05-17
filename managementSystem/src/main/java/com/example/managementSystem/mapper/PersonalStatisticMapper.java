package com.example.managementSystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface PersonalStatisticMapper {

    /**
     * 个人年度出差天数统计
     * @param userId
     * @return
     */
    @Select("select\n" +
            "  sum(days) as days,DATE_FORMAT(apply.apply_time,'%M') as month\n" +
            "from apply left join audit_application on audit_application.apply_id=apply.apply_id\n" +
            "where audit_application.is_pass=1 and user_id=#{userId} and DATE_FORMAT(apply_time,'%Y') =DATE_FORMAT(CURDATE(),'%Y')\n" +
            "group by month")
    List<Map<String,Object>> getDaysOfPersonalBusinessTripsThisYear(Integer userId);

    /**
     * 个人本年支出类型占比
     * @param userId
     * @return
     */
    @Select("select\n" +
            "  sum(transportation_expense) as \"transportation_expense\",\n" +
            "  sum(hotel_expense) as \"hotel_expense\",\n" +
            "  sum(catering_expenses) as \"catering_expenses\",\n" +
            "  sum(other_expenses) as \"other_expenses\"\n" +
            "from apply left join audit_application on audit_application.apply_id=apply.apply_id\n" +
            "where audit_application.is_pass=1 and apply.user_id=#{userId} and DATE_FORMAT(apply.apply_time,'%Y') = DATE_FORMAT(CURDATE(),'%Y')")
    Map<String,Object> getProportionOfPersonalExpenditureTypes(Integer userId);

    @Select("select\n" +
            "  city,count(*) as value\n" +
            "from apply left join audit_application on apply.apply_id=audit_application.apply_id\n" +
            "left join task on apply.task_id=task.task_id\n" +
            "left join address on task.address_id=address.address_id\n" +
            "where audit_application.is_pass=1 and apply.user_id=#{userId} and DATE_FORMAT(apply.apply_time,'%Y') = DATE_FORMAT(CURDATE(),'%Y')\n" +
            "group by city")
    List<Map<String,Object>> getBusinessTripCityStatistics(Integer userId);
}
