package com.example.managementSystem.mapper;

import com.example.managementSystem.entity.AuditApplication;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditApplicationMapper {
    int insert(AuditApplication record);

    int insertSelective(AuditApplication record);

    Integer countByApplyId(Integer applyId);
}