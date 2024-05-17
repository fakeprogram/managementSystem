package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.ReleaseTask;

public interface ReleaseTaskMapper {
    int deleteByPrimaryKey(Integer releaseId);

    int insert(ReleaseTask record);

    int insertSelective(ReleaseTask record);

    ReleaseTask selectByPrimaryKey(Integer releaseId);

    int updateByPrimaryKeySelective(ReleaseTask record);

    int updateByPrimaryKey(ReleaseTask record);
}