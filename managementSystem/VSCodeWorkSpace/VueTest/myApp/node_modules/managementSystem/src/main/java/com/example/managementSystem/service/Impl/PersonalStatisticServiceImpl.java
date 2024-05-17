package com.example.managementSystem.service.Impl;

import com.example.managementSystem.mapper.PersonalStatisticMapper;
import com.example.managementSystem.service.PersonalStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonalStatisticServiceImpl implements PersonalStatisticService {
    @Autowired
    private PersonalStatisticMapper personalStatisticMapper;

    public List<Map<String,Object>> getDaysOfPersonalBusinessTripsThisYear(Integer userId){
        List<Map<String, Object>> result = new ArrayList<>();
        result =personalStatisticMapper.getDaysOfPersonalBusinessTripsThisYear(userId);
        return result;
    }

    public Map<String,Object> getProportionOfPersonalExpenditureTypes(Integer userId){
        Map<String, Object> result = new HashMap<>();
        result =personalStatisticMapper.getProportionOfPersonalExpenditureTypes(userId);
        return result;
    }

    /**
     * 个人出差城市统计
     * @param userId
     * @return
     */
    public List<Map<String,Object>> getBusinessTripCityStatistics(Integer userId){
        List<Map<String, Object>> result = new ArrayList<>();
        result =personalStatisticMapper.getBusinessTripCityStatistics(userId);
        return result;
    }
}
