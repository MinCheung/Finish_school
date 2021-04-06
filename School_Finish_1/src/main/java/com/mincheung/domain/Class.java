package com.mincheung.domain;

import java.io.Serializable;
import java.util.Date;

public class Class  implements Serializable {
    /**班级id*/
    private  Integer classId;
    /**班级名称*/
    private String className;
    /**创建日期*/
    private Date createDate;
    /**班主任*/
    private  String teacher;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", createDate=" + createDate +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
