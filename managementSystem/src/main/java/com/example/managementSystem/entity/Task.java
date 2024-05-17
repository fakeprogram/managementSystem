package com.example.managementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * task
 *
 * @author
 */
public class Task implements Serializable {

    /**
     * 任务ID
     */
    private Integer taskId;



    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 部门ID
     */
    private Integer departmentId;

    /**
     * 地址
     */
    private Integer addressId;

    /**
     * 开始时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 任务文件
     */
    private String taskFile;

    /**
     * 任务进度
     */
    private Integer taskProgress;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 状态
     */
    private Integer taskStatus;

    /**
     * 详细信息
     */
    private String taskDetail;

    private static final long serialVersionUID = 1L;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTaskFile() {
        return taskFile;
    }

    public void setTaskFile(String taskFile) {
        this.taskFile = taskFile;
    }

    public Integer getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(Integer taskProgress) {
        this.taskProgress = taskProgress;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId) &&
                Objects.equals(taskName, task.taskName) &&
                Objects.equals(departmentId, task.departmentId) &&
                Objects.equals(addressId, task.addressId) &&
                Objects.equals(startTime, task.startTime) &&
                Objects.equals(endTime, task.endTime) &&
                Objects.equals(taskFile, task.taskFile) &&
                Objects.equals(taskProgress, task.taskProgress) &&
                Objects.equals(taskType, task.taskType) &&
                Objects.equals(taskStatus, task.taskStatus) &&
                Objects.equals(taskDetail, task.taskDetail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(taskId, taskName, departmentId, addressId, startTime, endTime, taskFile, taskProgress, taskType, taskStatus, taskDetail);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(",taskName").append(taskName);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", addressId=").append(addressId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", taskFile=").append(taskFile);
        sb.append(", taskProgress=").append(taskProgress);
        sb.append(", taskType=").append(taskType);
        sb.append(", taskStatus=").append(taskStatus);
        sb.append(", taskDetail=").append(taskDetail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}