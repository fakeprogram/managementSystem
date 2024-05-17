package com.example.managementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * release_task
 * @author 
 */
public class ReleaseTask implements Serializable {
    /**
     * 发布ID
     */
    private Integer releaseId;

    /**
     * 任务ID
     */
    private Integer taskId;

    /**
     * 发布人
     */
    private Integer publisher;

    /**
     * 发布时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    private static final long serialVersionUID = 1L;

    public Integer getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Integer releaseId) {
        this.releaseId = releaseId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
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
        ReleaseTask other = (ReleaseTask) that;
        return (this.getReleaseId() == null ? other.getReleaseId() == null : this.getReleaseId().equals(other.getReleaseId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getPublisher() == null ? other.getPublisher() == null : this.getPublisher().equals(other.getPublisher()))
            && (this.getReleaseTime() == null ? other.getReleaseTime() == null : this.getReleaseTime().equals(other.getReleaseTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReleaseId() == null) ? 0 : getReleaseId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getPublisher() == null) ? 0 : getPublisher().hashCode());
        result = prime * result + ((getReleaseTime() == null) ? 0 : getReleaseTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", releaseId=").append(releaseId);
        sb.append(", taskId=").append(taskId);
        sb.append(", publisher=").append(publisher);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}