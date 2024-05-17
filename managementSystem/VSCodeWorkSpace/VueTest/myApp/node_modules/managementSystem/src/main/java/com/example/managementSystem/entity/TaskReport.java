package com.example.managementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * task_report
 * @author 
 */
public class TaskReport implements Serializable {
    private Integer reportId;

    private Integer taskId;

    private Integer repoter;

    private Integer taskprogress;

    private String content;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportTime;

    private static final long serialVersionUID = 1L;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getRepoter() {
        return repoter;
    }

    public void setRepoter(Integer repoter) {
        this.repoter = repoter;
    }

    public Integer getTaskprogress() {
        return taskprogress;
    }

    public void setTaskprogress(Integer taskprogress) {
        this.taskprogress = taskprogress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
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
        TaskReport other = (TaskReport) that;
        return (this.getReportId() == null ? other.getReportId() == null : this.getReportId().equals(other.getReportId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getRepoter() == null ? other.getRepoter() == null : this.getRepoter().equals(other.getRepoter()))
            && (this.getTaskprogress() == null ? other.getTaskprogress() == null : this.getTaskprogress().equals(other.getTaskprogress()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getReportTime() == null ? other.getReportTime() == null : this.getReportTime().equals(other.getReportTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReportId() == null) ? 0 : getReportId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getRepoter() == null) ? 0 : getRepoter().hashCode());
        result = prime * result + ((getTaskprogress() == null) ? 0 : getTaskprogress().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getReportTime() == null) ? 0 : getReportTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reportId=").append(reportId);
        sb.append(", taskId=").append(taskId);
        sb.append(", repoter=").append(repoter);
        sb.append(", taskprogress=").append(taskprogress);
        sb.append(", content=").append(content);
        sb.append(", reportTime=").append(reportTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}