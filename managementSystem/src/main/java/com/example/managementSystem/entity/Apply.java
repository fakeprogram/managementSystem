package com.example.managementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * apply
 * @author 
 */
public class Apply implements Serializable {
    /**
     * 申请ID
     */
    private Integer applyId;

    /**
     * 任务ID
     */
    private Integer taskId;


    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 申请人
     */
    private Integer userId;

    /**
     * 申请人姓名
     */
    private String userName;

    /**
     * 申请人部门
     */

    private String departmentName;

    /**
     * 地址
     */

    private String address;

    /**
     * 申请时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;

    /**
     * 出发时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;

    /**
     * 返回时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime returnTime;

    /**
     * 申请状态
     */
    private Integer applyStatus;

    /**
     * 交通工具
     */
    private String vehicle;

    /**
     * 总费用
     */
    private BigDecimal totalCost;

    /**
     * 交通费
     */
    private BigDecimal transportationExpense;

    /**
     * 住宿费
     */
    private BigDecimal hotelExpense;

    /**
     * 餐饮费
     */
    private BigDecimal cateringExpenses;

    /**
     * 其他费用
     */
    private BigDecimal otherExpenses;

    /**
     * 出差天数
     */
    private Integer days;

    private static final long serialVersionUID = 1L;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTransportationExpense() {
        return transportationExpense;
    }

    public void setTransportationExpense(BigDecimal transportationExpense) {
        this.transportationExpense = transportationExpense;
    }

    public BigDecimal getHotelExpense() {
        return hotelExpense;
    }

    public void setHotelExpense(BigDecimal hotelExpense) {
        this.hotelExpense = hotelExpense;
    }

    public BigDecimal getCateringExpenses() {
        return cateringExpenses;
    }

    public void setCateringExpenses(BigDecimal cateringExpenses) {
        this.cateringExpenses = cateringExpenses;
    }

    public BigDecimal getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(BigDecimal otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
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
        Apply other = (Apply) that;
        return (this.getApplyId() == null ? other.getApplyId() == null : this.getApplyId().equals(other.getApplyId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getApplyTime() == null ? other.getApplyTime() == null : this.getApplyTime().equals(other.getApplyTime()))
            && (this.getDepartureTime() == null ? other.getDepartureTime() == null : this.getDepartureTime().equals(other.getDepartureTime()))
            && (this.getReturnTime() == null ? other.getReturnTime() == null : this.getReturnTime().equals(other.getReturnTime()))
            && (this.getApplyStatus() == null ? other.getApplyStatus() == null : this.getApplyStatus().equals(other.getApplyStatus()))
            && (this.getVehicle() == null ? other.getVehicle() == null : this.getVehicle().equals(other.getVehicle()))
            && (this.getTotalCost() == null ? other.getTotalCost() == null : this.getTotalCost().equals(other.getTotalCost()))
            && (this.getTransportationExpense() == null ? other.getTransportationExpense() == null : this.getTransportationExpense().equals(other.getTransportationExpense()))
            && (this.getHotelExpense() == null ? other.getHotelExpense() == null : this.getHotelExpense().equals(other.getHotelExpense()))
            && (this.getCateringExpenses() == null ? other.getCateringExpenses() == null : this.getCateringExpenses().equals(other.getCateringExpenses()))
            && (this.getOtherExpenses() == null ? other.getOtherExpenses() == null : this.getOtherExpenses().equals(other.getOtherExpenses()))
            && (this.getDays() == null ? other.getDays() == null : this.getDays().equals(other.getDays()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getApplyId() == null) ? 0 : getApplyId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getApplyTime() == null) ? 0 : getApplyTime().hashCode());
        result = prime * result + ((getDepartureTime() == null) ? 0 : getDepartureTime().hashCode());
        result = prime * result + ((getReturnTime() == null) ? 0 : getReturnTime().hashCode());
        result = prime * result + ((getApplyStatus() == null) ? 0 : getApplyStatus().hashCode());
        result = prime * result + ((getVehicle() == null) ? 0 : getVehicle().hashCode());
        result = prime * result + ((getTotalCost() == null) ? 0 : getTotalCost().hashCode());
        result = prime * result + ((getTransportationExpense() == null) ? 0 : getTransportationExpense().hashCode());
        result = prime * result + ((getHotelExpense() == null) ? 0 : getHotelExpense().hashCode());
        result = prime * result + ((getCateringExpenses() == null) ? 0 : getCateringExpenses().hashCode());
        result = prime * result + ((getOtherExpenses() == null) ? 0 : getOtherExpenses().hashCode());
        result = prime * result + ((getDays() == null) ? 0 : getDays().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", applyId=").append(applyId);
        sb.append(", taskId=").append(taskId);
        sb.append(", userId=").append(userId);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", departureTime=").append(departureTime);
        sb.append(", returnTime=").append(returnTime);
        sb.append(", applyStatus=").append(applyStatus);
        sb.append(", vehicle=").append(vehicle);
        sb.append(", totalCost=").append(totalCost);
        sb.append(", transportationExpense=").append(transportationExpense);
        sb.append(", hotelExpense=").append(hotelExpense);
        sb.append(", cateringExpenses=").append(cateringExpenses);
        sb.append(", otherExpenses=").append(otherExpenses);
        sb.append(", days=").append(days);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}