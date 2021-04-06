package com.mincheung.Responses;

import java.io.Serializable;

public class ResponseObject implements Serializable {
    //返回的用户编号
    private Integer userId;
    //返回的用户名
    private  String userName;
    //返回登录状态
    private  String state;
    //返回用户等级
    private String userGrade;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }
}
