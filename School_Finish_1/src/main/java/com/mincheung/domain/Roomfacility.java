package com.mincheung.domain;

import java.util.Date;

public class Roomfacility {
    /**设备编号*/
    private Integer facilityId;
    /**房间损坏描述*/
    private  String f_damageInfo;
    /**房间损坏日期*/
    private Date f_damageDate;
    /**解决状态*/
    private String f_resolveStatus;
    /**房间id*/
    private  Integer f_roomId;

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getF_damageInfo() {
        return f_damageInfo;
    }

    public void setF_damageInfo(String f_damageInfo) {
        this.f_damageInfo = f_damageInfo;
    }

    public Date getF_damageDate() {
        return f_damageDate;
    }

    public void setF_damageDate(Date f_damageDate) {
        this.f_damageDate = f_damageDate;
    }

    public String getF_resolveStatus() {
        return f_resolveStatus;
    }

    public void setF_resolveStatus(String f_resolveStatus) {
        this.f_resolveStatus = f_resolveStatus;
    }

    public Integer getF_roomId() {
        return f_roomId;
    }

    public void setF_roomId(Integer f_roomId) {
        this.f_roomId = f_roomId;
    }

    @Override
    public String toString() {
        return "Roomfacility{" +
                "facilityId=" + facilityId +
                ", f_damageInfo='" + f_damageInfo + '\'' +
                ", f_damageDate=" + f_damageDate +
                ", f_resolveStatus='" + f_resolveStatus + '\'' +
                ", f_roomId=" + f_roomId +
                '}';
    }
}
