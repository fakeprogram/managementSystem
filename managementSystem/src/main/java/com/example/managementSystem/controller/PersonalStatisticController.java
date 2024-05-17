package com.example.managementSystem.controller;

import com.example.managementSystem.service.PersonalStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/personalStatisticController")
public class PersonalStatisticController {

    @Autowired
    private PersonalStatisticService personalStatisticService;


    /**
     * 个人本年每月出差天数
     * @param userId
     * @return
     */
    @RequestMapping("/daysOfPersonalBusinessTripsThisYear")
    public Map<String,Object> getDaysOfPersonalBusinessTripsThisYear(@RequestParam(value = "user_id",required = false)Integer userId){
        Map<String, Object> map = new HashMap<>();
        if (userId == null) {
            map.put("success", false);
            map.put("msg", "缺少用户数据");
            return map;
        }
        List<Map<String, Object>> daysOfPersonalBusinessTrips = new ArrayList<>();
        daysOfPersonalBusinessTrips = personalStatisticService.getDaysOfPersonalBusinessTripsThisYear(userId);
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("data", daysOfPersonalBusinessTrips);
        return map;
    }


    /**
     * 个人本年支出类型占比
     * @param userId
     * @return
     */
    @RequestMapping("/proportionOfPersonalExpenditureTypes")
    public Map<String,Object> getProportionOfPersonalExpenditureTypes(@RequestParam(value = "user_id",required = false)Integer userId){
        Map<String, Object> map = new HashMap<>();
        if (userId == null) {
            map.put("success", false);
            map.put("msg", "缺少用户数据");
            return map;
        }
        Map<String, Object> proportion = new HashMap<>();
        proportion = personalStatisticService.getProportionOfPersonalExpenditureTypes(userId);
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("data", proportion);
        return map;
    }

    @RequestMapping("/businessTripCityStatistics")
    public Map<String,Object> getBusinessTripCityStatistics(@RequestParam(value = "user_id",required = false)Integer userId){
        Map<String, Object> map = new HashMap<>();
        if (userId == null) {
            map.put("success", false);
            map.put("msg", "缺少用户数据");
            return map;
        }
        List<Map<String, Object>> businessTripCitys = new ArrayList<>();
        businessTripCitys = personalStatisticService.getBusinessTripCityStatistics(userId);
        map.put("success", true);
        map.put("msg", "个人排名获取成功");
        map.put("data", businessTripCitys);
        return map;
    }
}
