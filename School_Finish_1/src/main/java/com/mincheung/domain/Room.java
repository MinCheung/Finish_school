package com.mincheung.domain;

import java.io.Serializable;
import java.util.IdentityHashMap;

public class Room implements Serializable {
    /**房间编号*/
    private Integer roomId;
    /**房间地址*/
    private  String f_roomAddress;
    /**房间容纳人数*/
    private Integer f_recipientNumber;
    /**房间状态*/
    private String f_status;
    /**房间类型（男生或女生）*/
    private  String f_roomType;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getF_roomAddress() {
        return f_roomAddress;
    }

    public void setF_roomAddress(String f_roomAddress) {
        this.f_roomAddress = f_roomAddress;
    }

    public Integer getF_recipientNumber() {
        return f_recipientNumber;
    }

    public void setF_recipientNumber(Integer f_recipientNumber) {
        this.f_recipientNumber = f_recipientNumber;
    }

    public String getF_status() {
        return f_status;
    }

    public void setF_status(String f_status) {
        this.f_status = f_status;
    }

    public String getF_roomType() {
        return f_roomType;
    }

    public void setF_roomType(String f_roomType) {
        this.f_roomType = f_roomType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", f_roomAddress='" + f_roomAddress + '\'' +
                ", f_recipientNumber=" + f_recipientNumber +
                ", f_status='" + f_status + '\'' +
                ", f_roomType='" + f_roomType + '\'' +
                '}';
    }
}
