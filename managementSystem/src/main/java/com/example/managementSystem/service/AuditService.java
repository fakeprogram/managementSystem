package com.example.managementSystem.service;

import com.example.managementSystem.entity.Apply;
import com.example.managementSystem.entity.AuditApplication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AuditService {
    public List<Apply> getUnAuditedApplication(Integer userId);
    public Integer auditOperation(AuditApplication auditApplication);
}
