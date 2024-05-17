package com.example.managementSystem.entity;

import java.io.Serializable;

/**
 * team
 * @author 
 */
public class Team implements Serializable {
    /**
     * 小组ID
     */
    private Integer teamId;

    /**
     * 小组负责人
     */
    private Integer teamHead;

    /**
     * 小组名称
     */
    private String teamName;

    private static final long serialVersionUID = 1L;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamHead() {
        return teamHead;
    }

    public void setTeamHead(Integer teamHead) {
        this.teamHead = teamHead;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
        Team other = (Team) that;
        return (this.getTeamId() == null ? other.getTeamId() == null : this.getTeamId().equals(other.getTeamId()))
            && (this.getTeamHead() == null ? other.getTeamHead() == null : this.getTeamHead().equals(other.getTeamHead()))
            && (this.getTeamName() == null ? other.getTeamName() == null : this.getTeamName().equals(other.getTeamName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeamId() == null) ? 0 : getTeamId().hashCode());
        result = prime * result + ((getTeamHead() == null) ? 0 : getTeamHead().hashCode());
        result = prime * result + ((getTeamName() == null) ? 0 : getTeamName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teamId=").append(teamId);
        sb.append(", teamHead=").append(teamHead);
        sb.append(", teamName=").append(teamName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}