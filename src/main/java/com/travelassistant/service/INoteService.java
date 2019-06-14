package com.travelassistant.service;

import com.travelassistant.common.ServerResponse;
import com.travelassistant.common.UserWithToken;
import com.travelassistant.pojo.Comment;
import com.travelassistant.pojo.NoteImage;
import com.travelassistant.pojo.TravelNote;

import java.util.List;

public interface INoteService {

    ServerResponse<TravelNote> publishNote(TravelNote travelNote);

    ServerResponse<String> checkNote(int userId, TravelNote travelNote);

    ServerResponse<TravelNote> getTravelNoteByAll(int userId, TravelNote travelNote);

    ServerResponse<List<TravelNote>> getTravelNoteByUserId(int userId);

    ServerResponse<List<TravelNote>> getAllTravelNote();

    ServerResponse<List<NoteImage>> getNoteImageByNoteId(int noteId);

    ServerResponse<List<Comment>> getCommentByNoteId(int noteId);

    ServerResponse<String> postComment(int noteId, String comment, UserWithToken user);

    ServerResponse<String> increaseBrowseTimes(int noteId);

    ServerResponse<List<Comment>> getMyComment(String username);

    ServerResponse<List<Comment>> getComment(int noteId);

    ServerResponse<TravelNote> getTravelNote(int noteId);

    ServerResponse<String> deleteNoteComment(Integer noteId);

    ServerResponse<String> deleteComment(Integer commentId);

    ServerResponse<String> deleteNoteImage(Integer noteId);

    ServerResponse<String> deleteImage(Integer imageId);

    ServerResponse<String> deleteTravelNote(Integer noteId);
}
