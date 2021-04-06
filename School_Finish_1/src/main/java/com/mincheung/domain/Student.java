package com.mincheung.domain;


import java.awt.*;
import java.io.Serializable;
import java.util.Date;

public class Student  implements Serializable {
    /**学生编号*/
    private  Integer studentId;
    /**学生姓名*/
    private String f_name;
    /**学生性别*/
    private String f_sex;
    /**学生班级*/
    private Integer f_classId;
    /**学生房间编号*/
    private Integer f_roomId;
    /**学生电话*/
    private String f_phone;
    /**学生照片路径*/
    private String f_face;
    /**学生加入时间*/
    private Date f_joinDate;
    /**新房间*/
    private Integer f_newRoom;
    /**读取的照片*/
    private Image f_image;


    public Image getF_image() {
        return f_image;
    }

    public void setF_image(Image f_image) {
        this.f_image = f_image;
    }

    public Integer getF_newRoom() {
        return f_newRoom;
    }

    public void setF_newRoom(Integer f_newRoom) {
        this.f_newRoom = f_newRoom;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_sex() {
        return f_sex;
    }

    public void setF_sex(String f_sex) {
        this.f_sex = f_sex;
    }

    public Integer getF_classId() {
        return f_classId;
    }

    public void setF_classId(Integer f_classId) {
        this.f_classId = f_classId;
    }

    public Integer getF_roomId() {
        return f_roomId;
    }

    public void setF_roomId(Integer f_roomId) {
        this.f_roomId = f_roomId;
    }

    public String getF_phone() {
        return f_phone;
    }

    public void setF_phone(String f_phone) {
        this.f_phone = f_phone;
    }

    public String getF_face() {
        return this.f_face;
    }

    public void setF_face(String f_face) {
        this.f_face = f_face;
    }

    public Date getF_joinDate() {
        return f_joinDate;
    }

    public void setF_joinDate(Date f_joinDate) {
        this.f_joinDate = f_joinDate;
    }



    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", f_name='" + f_name + '\'' +
                ", f_sex='" + f_sex + '\'' +
                ", f_classId=" + f_classId +
                ", f_roomId=" + f_roomId +
                ", f_phone='" + f_phone + '\'' +
                ", f_face='" + f_face + '\'' +
                ", f_joinDate=" + f_joinDate +
                ", f_newRoom=" + f_newRoom +
                ", f_image=" + f_image +
                '}';
    }
}
