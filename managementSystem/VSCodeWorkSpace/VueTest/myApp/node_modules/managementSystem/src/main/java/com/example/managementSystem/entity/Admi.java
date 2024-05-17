package com.example.managementSystem.entity;

import java.io.Serializable;

/**
 * admi
 * @author 
 */
public class Admi implements Serializable {
    /**
     * 管理员ID
     */
    private Integer admiId;

    /**
     * 管理员邮箱
     */
    private String admiEmail;

    /**
     * 管理员电话
     */
    private String admiTel;

    /**
     * 管理员密码
     */
    private String admiPass;

    /**
     * 管理员姓名
     */
    private String admiName;

    private static final long serialVersionUID = 1L;

    public Integer getAdmiId() {
        return admiId;
    }

    public void setAdmiId(Integer admiId) {
        this.admiId = admiId;
    }

    public String getAdmiEmail() {
        return admiEmail;
    }

    public void setAdmiEmail(String admiEmail) {
        this.admiEmail = admiEmail;
    }

    public String getAdmiTel() {
        return admiTel;
    }

    public void setAdmiTel(String admiTel) {
        this.admiTel = admiTel;
    }

    public String getAdmiPass() {
        return admiPass;
    }

    public void setAdmiPass(String admiPass) {
        this.admiPass = admiPass;
    }

    public String getAdmiName() {
        return admiName;
    }

    public void setAdmiName(String admiName) {
        this.admiName = admiName;
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
        Admi other = (Admi) that;
        return (this.getAdmiId() == null ? other.getAdmiId() == null : this.getAdmiId().equals(other.getAdmiId()))
            && (this.getAdmiEmail() == null ? other.getAdmiEmail() == null : this.getAdmiEmail().equals(other.getAdmiEmail()))
            && (this.getAdmiTel() == null ? other.getAdmiTel() == null : this.getAdmiTel().equals(other.getAdmiTel()))
            && (this.getAdmiPass() == null ? other.getAdmiPass() == null : this.getAdmiPass().equals(other.getAdmiPass()))
            && (this.getAdmiName() == null ? other.getAdmiName() == null : this.getAdmiName().equals(other.getAdmiName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAdmiId() == null) ? 0 : getAdmiId().hashCode());
        result = prime * result + ((getAdmiEmail() == null) ? 0 : getAdmiEmail().hashCode());
        result = prime * result + ((getAdmiTel() == null) ? 0 : getAdmiTel().hashCode());
        result = prime * result + ((getAdmiPass() == null) ? 0 : getAdmiPass().hashCode());
        result = prime * result + ((getAdmiName() == null) ? 0 : getAdmiName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", admiId=").append(admiId);
        sb.append(", admiEmail=").append(admiEmail);
        sb.append(", admiTel=").append(admiTel);
        sb.append(", admiPass=").append(admiPass);
        sb.append(", admiName=").append(admiName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}