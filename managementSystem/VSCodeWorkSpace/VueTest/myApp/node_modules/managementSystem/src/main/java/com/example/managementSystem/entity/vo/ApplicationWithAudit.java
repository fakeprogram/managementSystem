package com.example.managementSystem.entity.vo;

import com.example.managementSystem.entity.Apply;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationWithAudit extends Apply {

    /**
     * 审核人id
     */
    private Integer auditorId;

    /**
     * 审核人名称
     */
    private String auditorName;

    /**
     * 审核意见
     */
    private String opinions;

    /**
     * 审核时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    /**
     * 是否通过
     */
    private Integer isPass;

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getOpinions() {
        return opinions;
    }

    public void setOpinions(String opinions) {
        this.opinions = opinions;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getIsPass() {
        return isPass;
    }

    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }
}
