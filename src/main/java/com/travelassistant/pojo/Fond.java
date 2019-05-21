package com.travelassistant.pojo;

public class Fond {
    private Integer id;

    private Integer userId;

    private String scenicSpotName;

    public Fond(Integer id, Integer userId, String scenicSpotName) {
        this.id = id;
        this.userId = userId;
        this.scenicSpotName = scenicSpotName;
    }

    public Fond() {
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

    public String getScenicSpotName() {
        return scenicSpotName;
    }

    public void setScenicSpotName(String scenicSpotName) {
        this.scenicSpotName = scenicSpotName == null ? null : scenicSpotName.trim();
    }
}