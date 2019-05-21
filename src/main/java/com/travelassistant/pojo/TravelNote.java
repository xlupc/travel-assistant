package com.travelassistant.pojo;

import java.util.Date;

public class TravelNote {
    private Integer id;

    private Integer userId;

    private String noteTitle;

    private String noteBody;

    private String location;

    private Integer browseTimes;

    private Date createTime;

    private Date updateTime;

    public TravelNote(Integer id, Integer userId, String noteTitle, String noteBody, String location, Integer browseTimes, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.location = location;
        this.browseTimes = browseTimes;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public TravelNote() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle == null ? null : noteTitle.trim();
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody == null ? null : noteBody.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(Integer browseTimes) {
        this.browseTimes = browseTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}