package com.travelassistant.service.impl;

import com.travelassistant.common.ServerResponse;
import com.travelassistant.common.UserWithToken;
import com.travelassistant.dao.CommentMapper;
import com.travelassistant.dao.NoteImageMapper;
import com.travelassistant.dao.TravelNoteMapper;
import com.travelassistant.pojo.Comment;
import com.travelassistant.pojo.NoteImage;
import com.travelassistant.pojo.TravelNote;
import com.travelassistant.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iNoteService")
public class NoteServiceImpl implements INoteService {

    @Autowired
    private TravelNoteMapper travelNoteMapper;
    @Autowired
    private NoteImageMapper noteImageMapper;
    @Autowired
    private CommentMapper commentMapper;

    public ServerResponse<TravelNote> publishNote(TravelNote travelNote){
        int resultCount = travelNoteMapper.insert(travelNote);
        if(resultCount == 0){
            return ServerResponse.createByErrorMsg("发布失败，请重新发布");
        }
        return ServerResponse.createBySuccess("发布成功", travelNote);
    }

    public ServerResponse<String> checkNote(int userId, TravelNote travelNote){
        int resultCount = travelNoteMapper.checkTravelNote(userId,travelNote.getNoteTitle(),travelNote.getNoteBody());
        if(resultCount > 0 ){
            return ServerResponse.createByErrorMsg("相似攻略/游记已存在，请修改后再次发布");
        }
        return ServerResponse.createBySuccessMsg("校验成功");
    }

    public ServerResponse<TravelNote> getTravelNoteByAll(int userId, TravelNote travelNote) {
        TravelNote travelNote1 = travelNoteMapper.selectTravelNote(userId,travelNote.getNoteTitle(),travelNote.getNoteBody());
        if(travelNote1 == null) {
            return ServerResponse.createByErrorMsg("查找游记失败");
        }
        return ServerResponse.createBySuccess("查找成功", travelNote1);
    }

    public ServerResponse<List<TravelNote>> getTravelNoteByUserId(int userId) {
        List<TravelNote> list =  travelNoteMapper.selectTravelNoteByUserId(userId);
        return ServerResponse.createBySuccess("查找成功", list);
    }

    public ServerResponse<List<NoteImage>> getNoteImageByNoteId(int noteId) {
        List<NoteImage> list = noteImageMapper.selectNoteImageByNoteId(noteId);
        return ServerResponse.createBySuccess("查找成功", list);
    }

    public ServerResponse<List<Comment>> getCommentByNoteId(int noteId) {
        List<Comment> list = commentMapper.selectCommentByNoteId(noteId);
        return ServerResponse.createBySuccess("查找成功", list);
    }

    public ServerResponse<List<TravelNote>> getAllTravelNote() {
        List<TravelNote> list =  travelNoteMapper.selectAllTravelNote();
        return ServerResponse.createBySuccess("查找成功", list);
    }

    public ServerResponse<String> postComment(int noteId, String comment, UserWithToken user) {
        Comment comment1 = new Comment();
        comment1.setNoteId(noteId);
        comment1.setComment(comment);
        comment1.setAvatarPath("/images/avatar.png");
        comment1.setUsername(user.getUsername());
        int resultCount = commentMapper.insert(comment1);
        if(resultCount == 0){
            return ServerResponse.createByErrorMsg("评论发布失败");
        }
        return ServerResponse.createBySuccessMsg("评论发布成功");
    }

    public ServerResponse<String> increaseBrowseTimes(int noteId){
        int updateCount = travelNoteMapper.increaseBrowseTimes(noteId);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMsg("浏览次数加一");
        }
        return ServerResponse.createByErrorMsg("浏览次数增加失败");
    }

    public ServerResponse<List<Comment>> getMyComment(String username) {
        List<Comment> list = commentMapper.selectCommentByUsername(username);
        return ServerResponse.createBySuccess("查找成功", list);
    }

    public ServerResponse<List<Comment>> getComment(int noteId) {
        List<Comment> list = commentMapper.selectCommentByNoteId(noteId);
        return ServerResponse.createBySuccess("查找游记评论成功", list);
    }

    public ServerResponse<TravelNote> getTravelNote(int noteId) {
        TravelNote travelNote =  travelNoteMapper.selectByPrimaryKey(noteId);
        return ServerResponse.createBySuccess("查找成功", travelNote);
    }
}
