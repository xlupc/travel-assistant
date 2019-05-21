package com.travelassistant.dao;

import com.travelassistant.pojo.TravelNote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelNoteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TravelNote record);

    int insertSelective(TravelNote record);

    TravelNote selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelNote record);

    int updateByPrimaryKey(TravelNote record);

    TravelNote selectTravelNote(@Param("userId") Integer userId, @Param("noteTitle") String noteTitle, @Param("noteBody") String noteBody);

    List<TravelNote> selectTravelNoteByUserId(@Param("userId") Integer userId);

    List<TravelNote> selectAllTravelNote();

    int checkTravelNote(@Param("userId") Integer userId, @Param("noteTitle") String noteTitle, @Param("noteBody") String noteBody);

    int increaseBrowseTimes(@Param("noteId") Integer noteId);
}