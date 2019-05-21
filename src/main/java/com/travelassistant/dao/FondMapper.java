package com.travelassistant.dao;

import com.travelassistant.pojo.Fond;

public interface FondMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Fond record);

    int insertSelective(Fond record);

    Fond selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Fond record);

    int updateByPrimaryKey(Fond record);
}