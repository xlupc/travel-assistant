package com.travelassistant.pojo;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer noteId;

    private String comment;

    private String username;

    private String avatarPath;

    private Date createTime;

    private Date updateTime;

    public Comment(Integer id, Integer noteId, String comment, String username, String avatarPath, Date createTime, Date updateTime) {
        this.id = id;
        this.noteId = noteId;
        this.comment = comment;
        this.username = username;
        this.avatarPath = avatarPath;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Comment() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
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