package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.Admi;

public interface AdmiMapper {
    int deleteByPrimaryKey(Integer admiId);

    int insert(Admi record);

    int insertSelective(Admi record);

    Admi selectByPrimaryKey(Integer admiId);

    int updateByPrimaryKeySelective(Admi record);

    int updateByPrimaryKey(Admi record);
}