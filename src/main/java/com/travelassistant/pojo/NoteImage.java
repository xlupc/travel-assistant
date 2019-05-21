package com.travelassistant.pojo;

import java.util.Date;

public class NoteImage {
    private Integer id;

    private Integer noteId;

    private String imgPath;

    private Date createTime;

    private Date updateTime;

    public NoteImage(Integer id, Integer noteId, String imgPath, Date createTime, Date updateTime) {
        this.id = id;
        this.noteId = noteId;
        this.imgPath = imgPath;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public NoteImage() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
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