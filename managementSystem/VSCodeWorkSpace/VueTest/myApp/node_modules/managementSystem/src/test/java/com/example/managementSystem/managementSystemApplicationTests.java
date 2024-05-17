package com.example.managementSystem;

import com.example.managementSystem.mapper.DepartmentStatisticMapper;
import com.example.managementSystem.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class managementSystemApplicationTests {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    DepartmentStatisticMapper departmentStatisticMapper;

    @Test
    void contextLoads() {
        LocalDate date =LocalDate.now();
//        System.out.println("Date:"+date+'\n');
//        System.out.println(usersMapper.getOutSideStaffAttendanceOfToday(date.minusDays(1))+"\n");
//        System.out.println(usersMapper.getAllOutSideStaff()+"\n");
        System.out.println(departmentStatisticMapper.getPersonalBusinessTravelTimeRankings(2024)+"\n");
    }
}
