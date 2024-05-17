package com.example.managementSystem.service.Impl;

import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.mapper.DepartmentStatisticMapper;
import com.example.managementSystem.mapper.UsersMapper;
import com.example.managementSystem.service.DepartmentStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentStatisticServiceImpl implements DepartmentStatisticService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private DepartmentStatisticMapper departmentStatisticMapper;

    /**
     * 获取所有在外人员
     *
     * @return
     */
    @Override
    public List<UserWithDepartment> getAllOutSideStaff() {
        List<UserWithDepartment> userWithDepartment = new ArrayList<>();
        userWithDepartment = usersMapper.getAllOutSideStaff();
        return userWithDepartment;
    }


    /**
     * 获取本年员工出差天数排名
     *
     * @return
     */
    public List<Map<String, Object>> getPersonalBusinessTravelTimeRankings() {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate date =LocalDate.now();
        result =departmentStatisticMapper.getPersonalBusinessTravelTimeRankings(date.getYear());
        return result;
    }

    /**
     * 获取任务数
     * @return
     */
    public Integer getNumOfTasks(){
        Integer count =0;
        count=departmentStatisticMapper.getNumOfTasks();
        return count;
    }


    /**
     * 获取今日在外人员分布
     * @return
     */
    public List<Map<String,Object>> getDistributionOfExpatriates(){
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate date =LocalDate.now();
        result =departmentStatisticMapper.getDistributionOfExpatriates(date);
        return result;
    }

    /**
     * 本月XX部门支出类型占比
     * @param departmentId
     * @return
     */
    public Map<String,Object> getProportionOfDepartmentalExpenditureTypes(Integer departmentId){
        Map<String, Object> result = new HashMap<>();
        result =departmentStatisticMapper.getProportionOfDepartmentalExpenditureTypes(departmentId);
        return result;
    }

    /**
     * XX部门年度支出统计
     * @param departmentId
     * @return
     */
    public List<Map<String,Object>> getDepartmentalAnnualExpenditure(Integer departmentId){
        List<Map<String, Object>> result = new ArrayList<>();
        result =departmentStatisticMapper.getDepartmentalAnnualExpenditure(departmentId);
        return result;
    }

    /**
     * 各个部门本月总支出统计
     * @return
     */
    public List<Map<String,Object>> getMonthlyExpenditureOfEachDepartment(){
        List<Map<String, Object>> result = new ArrayList<>();
        result =departmentStatisticMapper.getMonthlyExpenditureOfEachDepartment();
        return result;
    }

    /**
     * XX部门本月交通方式占比
     * @param departmentId
     * @return
     */
    public List<Map<String,Object>> getProportionOfTransportationModesInDepartments(Integer departmentId){
        List<Map<String, Object>> result = new ArrayList<>();
        result =departmentStatisticMapper.getProportionOfTransportationModesInDepartments(departmentId);
        return result;
    }
}
