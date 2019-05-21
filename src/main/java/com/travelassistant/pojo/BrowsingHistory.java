package com.travelassistant.pojo;

import java.util.Date;

public class BrowsingHistory {
    private Integer id;

    private Integer userId;

    private Integer noteId;

    private Date createTime;

    private Date updateTime;

    public BrowsingHistory(Integer id, Integer userId, Integer noteId, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.noteId = noteId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public BrowsingHistory() {
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

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
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