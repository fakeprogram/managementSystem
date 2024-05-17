package com.example.managementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * audit_application
 * @author 
 */
public class AuditApplication implements Serializable {
    /**
     * 申请id
     */
    private Integer applyId;

    /**
     * 审核人
     */
    private Integer auditor;

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

    private static final long serialVersionUID = 1L;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getAuditor() {
        return auditor;
    }

    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AuditApplication other = (AuditApplication) that;
        return (this.getApplyId() == null ? other.getApplyId() == null : this.getApplyId().equals(other.getApplyId()))
            && (this.getAuditor() == null ? other.getAuditor() == null : this.getAuditor().equals(other.getAuditor()))
            && (this.getOpinions() == null ? other.getOpinions() == null : this.getOpinions().equals(other.getOpinions()))
            && (this.getAuditTime() == null ? other.getAuditTime() == null : this.getAuditTime().equals(other.getAuditTime()))
            && (this.getIsPass() == null ? other.getIsPass() == null : this.getIsPass().equals(other.getIsPass()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getApplyId() == null) ? 0 : getApplyId().hashCode());
        result = prime * result + ((getAuditor() == null) ? 0 : getAuditor().hashCode());
        result = prime * result + ((getOpinions() == null) ? 0 : getOpinions().hashCode());
        result = prime * result + ((getAuditTime() == null) ? 0 : getAuditTime().hashCode());
        result = prime * result + ((getIsPass() == null) ? 0 : getIsPass().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", applyId=").append(applyId);
        sb.append(", auditor=").append(auditor);
        sb.append(", opinions=").append(opinions);
        sb.append(", auditTime=").append(auditTime);
        sb.append(", isPass=").append(isPass);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}