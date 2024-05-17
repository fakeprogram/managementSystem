package com.example.managementSystem.controller;

import com.example.managementSystem.entity.vo.UserWithDepartment;
import com.example.managementSystem.entity.vo.UserWithDepartmentAndAttendance;
import com.example.managementSystem.service.AttendanceService;
import com.example.managementSystem.service.DepartmentStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departmentStatistic")
public class DepartmentStatisticController {

    @Autowired
    private DepartmentStatisticService departmentStatisticService;

    /**
     * 获取任务数
     *
     * @return
     */
    @RequestMapping("/getNumOfTasks")
    public Map<String, Object> getNumOfTasks() {
        Map<String, Object> map = new HashMap<>();
        Integer numOfTasks = 0;
        numOfTasks = departmentStatisticService.getNumOfTasks();
        map.put("success", true);
        map.put("msg", "获取所有人员数据成功");
        map.put("data", numOfTasks);
        return map;
    }

    /**
     * 获取所有在外人员
     *
     * @return
     */
    @RequestMapping("/getAllOutSideStaff")
    public Map<String, Object> getAllOutSideStaff() {
        Map<String, Object> map = new HashMap<>();
        List<UserWithDepartment> userWithDepartment = new ArrayList<>();
        userWithDepartment = departmentStatisticService.getAllOutSideStaff();
        map.put("success", true);
        map.put("msg", "获取所有人员数据成功");
        map.put("data", userWithDepartment);
        return map;
    }

    /**
     * 获取本年个人出差时长排名
     *
     * @returnP
     */
    @RequestMapping("/getPersonalBusinessTravelTimeRankings")
    public Map<String, Object> getPersonalBusinessTravelTimeRankings() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> rankings = new ArrayList<>();
        rankings = departmentStatisticService.getPersonalBusinessTravelTimeRankings();
        map.put("success", true);
        map.put("msg", "个人排名获取成功");
        map.put("data", rankings);
        return map;
    }

    /**
     * 获取在外人员位置分布
     *
     * @return
     */
    @RequestMapping("/geographicalDistributionOfExpatriates")
    public Map<String, Object> geographicalDistributionOfExpatriates() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> distribution = new ArrayList<>();
        distribution = departmentStatisticService.getDistributionOfExpatriates();
        map.put("success", true);
        map.put("msg", "个人排名获取成功");
        map.put("data", distribution);
        return map;
    }

    /**
     * 本月XX部门支出类型占比
     *
     * @param departmentId
     * @return
     */
    @RequestMapping("/proportionOfDepartmentalExpenditureTypes")
    public Map<String, Object> proportionOfDepartmentalExpenditureTypes(@RequestParam(value = "departmentId", required = false) Integer departmentId) {
        Map<String, Object> map = new HashMap<>();
        if (departmentId == null) {
            map.put("success", false);
            map.put("msg", "缺少部门数据");
            return map;
        }
        Map<String, Object> proportion = new HashMap<>();
        proportion = departmentStatisticService.getProportionOfDepartmentalExpenditureTypes(departmentId);
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("data", proportion);
        return map;
    }

    /**
     * XX部门年度支出数据统计
     *
     * @param departmentId
     * @return
     */
    @RequestMapping("/departmentalAnnualExpenditure")
    public Map<String, Object> departmentalAnnualExpenditure(@RequestParam(value = "departmentId", required = false) Integer departmentId) {
        Map<String, Object> map = new HashMap<>();
        if (departmentId == null) {
            map.put("success", false);
            map.put("msg", "缺少部门数据");
            return map;
        }
        List<Map<String, Object>> annualExpenditure = new ArrayList<>();
        annualExpenditure = departmentStatisticService.getDepartmentalAnnualExpenditure(departmentId);
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("data", annualExpenditure);
        return map;
    }

    /**
     * 各个部门本月支出统计
     *
     * @return
     */
    @RequestMapping("/monthlyExpenditureOfEachDepartment")
    public Map<String, Object> monthlyExpenditureOfEachDepartment() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> monthlyExpenditure = new ArrayList<>();
        monthlyExpenditure = departmentStatisticService.getMonthlyExpenditureOfEachDepartment();
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("data", monthlyExpenditure);
        return map;
    }

    /**
     * XX部门本月交通方式占比
     * @param departmentId
     * @return
     */
    @RequestMapping("/proportionOfTransportationModesInDepartments")
    public Map<String, Object> getProportionOfTransportationModesInDepartments(@RequestParam(value = "departmentId", required = false) Integer departmentId) {
        Map<String, Object> map = new HashMap<>();
        if (departmentId == null) {
            map.put("success", false);
            map.put("msg", "缺少部门数据");
            return map;
        }
        List<Map<String, Object>> proportion = new ArrayList<>();
        proportion = departmentStatisticService.getProportionOfTransportationModesInDepartments(departmentId);
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("data", proportion);
        return map;
    }
}
