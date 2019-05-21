package com.travelassistant.dao;

import com.travelassistant.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectCommentByNoteId(@Param("noteId") Integer noteId);

    List<Comment> selectCommentByUsername(@Param("username") String username);

    int updateUsername(@Param("newName")String newName, @Param("oldName")String oldName);
}