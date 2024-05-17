package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.JoinTeam;

public interface JoinTeamMapper {
    int insert(JoinTeam record);

    int insertSelective(JoinTeam record);
}