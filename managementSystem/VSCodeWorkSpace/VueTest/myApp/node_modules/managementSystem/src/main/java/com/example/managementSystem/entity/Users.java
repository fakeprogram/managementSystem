package com.example.managementSystem.entity;

import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * users
 * @author 
 */
@NoArgsConstructor
public class Users implements Serializable {
    /**
     * 用户ID
     */
    private Integer user_id;

    /**
     * 部门
     */
    private Integer department_id;

    /**
     * 电话
     */
    private String user_tel;

    /**
     * 密码
     */
    private String pass_word;

    /**
     * 姓名
     */
    private String user_name;

    /**
     * 邮箱
     */
    private String user_email;

    /**
     * 头像
     */
    private String head;

    /**
     * 权限类型
     */
    private Integer role_type;

    /**
     * 状态（是否出差）
     */
    private Integer user_status;

    /**
     * 是否登陆
     */
    private Integer is_login;

    private static final long serialVersionUID = 1L;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Integer getRole_type() {
        return role_type;
    }

    public void setRole_type(Integer role_type) {
        this.role_type = role_type;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public Integer getIs_login() {
        return is_login;
    }

    public void setIs_login(Integer is_login) {
        this.is_login = is_login;
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
        Users other = (Users) that;
        return (this.getUser_id() == null ? other.getUser_id() == null : this.getUser_id().equals(other.getUser_id()))
            && (this.getDepartment_id() == null ? other.getDepartment_id() == null : this.getDepartment_id().equals(other.getDepartment_id()))
            && (this.getUser_tel() == null ? other.getUser_tel() == null : this.getUser_tel().equals(other.getUser_tel()))
            && (this.getPass_word() == null ? other.getPass_word() == null : this.getPass_word().equals(other.getPass_word()))
            && (this.getUser_name() == null ? other.getUser_name() == null : this.getUser_name().equals(other.getUser_name()))
            && (this.getUser_email() == null ? other.getUser_email() == null : this.getUser_email().equals(other.getUser_email()))
            && (this.getHead() == null ? other.getHead() == null : this.getHead().equals(other.getHead()))
            && (this.getRole_type() == null ? other.getRole_type() == null : this.getRole_type().equals(other.getRole_type()))
            && (this.getUser_status() == null ? other.getUser_status() == null : this.getUser_status().equals(other.getUser_status()))
            && (this.getIs_login() == null ? other.getIs_login() == null : this.getIs_login().equals(other.getIs_login()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUser_id() == null) ? 0 : getUser_id().hashCode());
        result = prime * result + ((getDepartment_id() == null) ? 0 : getDepartment_id().hashCode());
        result = prime * result + ((getUser_tel() == null) ? 0 : getUser_tel().hashCode());
        result = prime * result + ((getPass_word() == null) ? 0 : getPass_word().hashCode());
        result = prime * result + ((getUser_name() == null) ? 0 : getUser_name().hashCode());
        result = prime * result + ((getUser_email() == null) ? 0 : getUser_email().hashCode());
        result = prime * result + ((getHead() == null) ? 0 : getHead().hashCode());
        result = prime * result + ((getRole_type() == null) ? 0 : getRole_type().hashCode());
        result = prime * result + ((getUser_status() == null) ? 0 : getUser_status().hashCode());
        result = prime * result + ((getIs_login() == null) ? 0 : getIs_login().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", user_id=").append(user_id);
        sb.append(", department_id=").append(department_id);
        sb.append(", user_tel=").append(user_tel);
        sb.append(", pass_word=").append(pass_word);
        sb.append(", user_name=").append(user_name);
        sb.append(", user_email=").append(user_email);
        sb.append(", head=").append(head);
        sb.append(", role_type=").append(role_type);
        sb.append(", user_status=").append(user_status);
        sb.append(", is_login=").append(is_login);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}