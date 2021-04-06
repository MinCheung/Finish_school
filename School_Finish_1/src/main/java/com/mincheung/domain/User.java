package com.mincheung.domain;

import java.io.Serializable;

public class User implements Serializable {
    /**用户编号*/
    private Integer userId;
    /**用户名*/
    private String  f_userName;
    /**密码*/
    private String  f_password;
    /**用户真实姓名*/
    private String  f_realName;
    /**用户等级*/
    private  String f_grade;
    /**用户旧密码*/
    private String OldPsw;

    public String getOldPsw() {
        return OldPsw;
    }

    public void setOldPsw(String oldPsw) {
        OldPsw = oldPsw;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getF_userName() {
        return f_userName;
    }

    public void setF_userName(String f_userName) {
        this.f_userName = f_userName;
    }

    public String getF_password() {
        return f_password;
    }

    public void setF_password(String f_password) {
        this.f_password = f_password;
    }

    public String getF_realName() {
        return f_realName;
    }

    public void setF_realName(String f_realName) {
        this.f_realName = f_realName;
    }

    public String getF_grade() {
        return f_grade;
    }

    public void setF_grade(String f_grade) {
        this.f_grade = f_grade;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", f_userName='" + f_userName + '\'' +
                ", f_password='" + f_password + '\'' +
                ", f_realName='" + f_realName + '\'' +
                ", f_grade='" + f_grade + '\'' +
                '}';
    }
}
