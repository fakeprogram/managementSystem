package com.example.managementSystem.service;

import com.example.managementSystem.entity.vo.UserWithDepartment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DepartmentStatisticService {
    public List<UserWithDepartment> getAllOutSideStaff();

    public List<Map<String,Object>> getPersonalBusinessTravelTimeRankings();

    public Integer getNumOfTasks();

    public List<Map<String,Object>> getDistributionOfExpatriates();

    public Map<String,Object> getProportionOfDepartmentalExpenditureTypes(Integer departmentId);

    public List<Map<String,Object>> getDepartmentalAnnualExpenditure(Integer departmentId);

    public List<Map<String,Object>> getMonthlyExpenditureOfEachDepartment();

    public List<Map<String,Object>> getProportionOfTransportationModesInDepartments(Integer departmentId);
}
