package com.travelassistant.dao;

import com.travelassistant.pojo.BrowsingHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrowsingHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrowsingHistory record);

    int insertSelective(BrowsingHistory record);

    BrowsingHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrowsingHistory record);

    int updateByPrimaryKey(BrowsingHistory record);

    List<BrowsingHistory> selectByUserId(@Param("userId")Integer userId);

    int deleteByUserId(@Param("userId")Integer userId);
}