package com.example.managementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * task_assignments
 * @author 
 */
public class TaskAssignments implements Serializable {
    /**
     * 主键
     */
    private Integer assignmentId;

    /**
     * 分派人
     */
    private Integer appointor;

    /**
     * 接收人
     */
    private Integer receiver;

    /**
     * 任务ID
     */
    private Integer taskId;

    /**
     * 签收日期
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signForTime;

    /**
     * 分配日期
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assignmentDate;

    /**
     * 分配状态
     */
    private Integer assignmentStatus;

    private static final long serialVersionUID = 1L;

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getAppointor() {
        return appointor;
    }

    public void setAppointor(Integer appointor) {
        this.appointor = appointor;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getSignForTime() {
        return signForTime;
    }

    public void setSignForTime(LocalDateTime signForTime) {
        this.signForTime = signForTime;
    }

    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public Integer getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(Integer assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
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
        TaskAssignments other = (TaskAssignments) that;
        return (this.getAssignmentId() == null ? other.getAssignmentId() == null : this.getAssignmentId().equals(other.getAssignmentId()))
            && (this.getAppointor() == null ? other.getAppointor() == null : this.getAppointor().equals(other.getAppointor()))
            && (this.getReceiver() == null ? other.getReceiver() == null : this.getReceiver().equals(other.getReceiver()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getSignForTime() == null ? other.getSignForTime() == null : this.getSignForTime().equals(other.getSignForTime()))
            && (this.getAssignmentDate() == null ? other.getAssignmentDate() == null : this.getAssignmentDate().equals(other.getAssignmentDate()))
            && (this.getAssignmentStatus() == null ? other.getAssignmentStatus() == null : this.getAssignmentStatus().equals(other.getAssignmentStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAssignmentId() == null) ? 0 : getAssignmentId().hashCode());
        result = prime * result + ((getAppointor() == null) ? 0 : getAppointor().hashCode());
        result = prime * result + ((getReceiver() == null) ? 0 : getReceiver().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getSignForTime() == null) ? 0 : getSignForTime().hashCode());
        result = prime * result + ((getAssignmentDate() == null) ? 0 : getAssignmentDate().hashCode());
        result = prime * result + ((getAssignmentStatus() == null) ? 0 : getAssignmentStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", assignmentId=").append(assignmentId);
        sb.append(", appointor=").append(appointor);
        sb.append(", receiver=").append(receiver);
        sb.append(", taskId=").append(taskId);
        sb.append(", signForTime=").append(signForTime);
        sb.append(", assignmentDate=").append(assignmentDate);
        sb.append(", assignmentStatus=").append(assignmentStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}