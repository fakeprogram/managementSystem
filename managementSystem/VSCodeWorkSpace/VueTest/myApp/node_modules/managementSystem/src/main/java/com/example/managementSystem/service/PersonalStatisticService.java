package com.example.managementSystem.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PersonalStatisticService {
    public List<Map<String,Object>> getDaysOfPersonalBusinessTripsThisYear(Integer userId);

    public Map<String,Object> getProportionOfPersonalExpenditureTypes(Integer userId);

    public List<Map<String,Object>> getBusinessTripCityStatistics(Integer userId);
}
