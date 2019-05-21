package com.travelassistant.dao;

import com.travelassistant.pojo.NoteImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoteImage record);

    int insertSelective(NoteImage record);

    NoteImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoteImage record);

    int updateByPrimaryKey(NoteImage record);

    List<NoteImage> selectNoteImageByNoteId(@Param("noteId") Integer noteId);

}