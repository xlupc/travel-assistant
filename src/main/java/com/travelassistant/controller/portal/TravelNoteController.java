package com.travelassistant.controller.portal;

import com.travelassistant.common.Const;
import com.travelassistant.common.ResponseCode;
import com.travelassistant.common.ServerResponse;
import com.travelassistant.common.UserWithToken;
import com.travelassistant.pojo.Comment;
import com.travelassistant.pojo.NoteImage;
import com.travelassistant.pojo.TravelNote;
import com.travelassistant.pojo.User;
import com.travelassistant.service.IFileService;
import com.travelassistant.service.INoteService;
import com.travelassistant.service.IUserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/note/")
public class TravelNoteController {

    @Autowired
    private IFileService iFileService;
    @Autowired
    private INoteService iNoteService;
    @Autowired
    private IUserService iUserService;

//    @RequestMapping("upload.do")
//    @ResponseBody
//    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
//        UserWithToken user = (UserWithToken)session.getAttribute(Const.CURRENT_USER);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请先登录后发布");
//        }
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileName = iFileService.upload(file,path);
////            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
//            String url = "";
//            Map fileMap = Maps.newHashMap();
//            fileMap.put("uri",targetFileName);
//            fileMap.put("url",url);
//            return ServerResponse.createBySuccess(fileMap);
//    }

    @RequestMapping(value = "publish_note.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse publishNote(HttpSession session, HttpServletRequest request) {
        UserWithToken user = (UserWithToken)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请先登录后发布");
        }

        ServerResponse<TravelNote> serverResponse;

        TravelNote travelNote = new TravelNote();
        travelNote.setBrowseTimes(0);
        travelNote.setUserId(user.getId());

        try {

            //1 工厂
            FileItemFactory fileItemFactory = new DiskFileItemFactory();

            //2 核心类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);

            //3 解析request  ,List存放 FileItem （表单元素的封装对象，一个<input>对应一个对象）
            List<FileItem> list = servletFileUpload.parseRequest(request);

            //4 遍历集合获得数据
            for (FileItem fileItem : list) {
                if(fileItem.isFormField()){
                    // 5 是否为表单字段（普通表单元素）
                    //5.1.表单字段名称
                    System.out.println("1111111");
                    String fieldName = fileItem.getFieldName();
                    System.out.println(fieldName);
                    //5.2.表单字段值
                    String fieldValue = fileItem.getString("utf-8");    //中文会出现乱码
                    System.out.println(fieldValue);
                    if (fieldName.equals("noteTitle")) { // 对应form中属性的名字
                        travelNote.setNoteTitle(fieldValue);
                    } else if (fileItem.getFieldName().equals("noteBody")) {
                        travelNote.setNoteBody(fieldValue);
                    } else  if (fileItem.getFieldName().equals("location")) {
                        travelNote.setLocation(fieldValue);
                    }
                }
            }

            ServerResponse<String> checkResponse = iNoteService.checkNote(user.getId(),travelNote);
            if(!checkResponse.isSuccess()) {
                return checkResponse;
            }

            iNoteService.publishNote(travelNote);

            serverResponse = iNoteService.getTravelNoteByAll(user.getId(),travelNote);

            for (FileItem fileItem : list) {
                if(!fileItem.isFormField()){
                    System.out.println(serverResponse.getData().getId());
                    System.out.println("999999");
                    String path = request.getSession().getServletContext().getRealPath("images");
                    ServerResponse imgResponse = iFileService.upload(fileItem,path,serverResponse.getData().getId());
                    if(!imgResponse.isSuccess()){
                        return imgResponse;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return ServerResponse.createBySuccess("发布成功", serverResponse.getData());

//        select * from 产品表 left join 图片表 on 产品表.id=图片表.product_id
    }

    @RequestMapping(value = "get_user_travel_note.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Map>> getTravelNoteByUserId(HttpSession session) {
        UserWithToken currentUser = (UserWithToken)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        List<Map> res = new ArrayList<>();
        ServerResponse<List<TravelNote>> travelNoteResponse =  iNoteService.getTravelNoteByUserId(currentUser.getId());
        for(TravelNote travelNote: travelNoteResponse.getData()) {
            ServerResponse<List<NoteImage>> noteImageResponse = iNoteService.getNoteImageByNoteId(travelNote.getId());
            ServerResponse<List<Comment>> commentResponse = iNoteService.getCommentByNoteId(travelNote.getId());
            ServerResponse<User> userServerResponse  = iUserService.getInformation(travelNote.getUserId());
            Map map = new HashMap();
            map.put("id",travelNote.getId());
            map.put("username",userServerResponse.getData().getUsername());
            map.put("noteTitle",travelNote.getNoteTitle());
            map.put("noteBody",travelNote.getNoteBody());
            map.put("location",travelNote.getLocation());
            map.put("browseTimes",travelNote.getBrowseTimes());
            map.put("createTime",travelNote.getCreateTime());
            map.put("updateTime",travelNote.getUpdateTime());
            map.put("images",noteImageResponse.getData());
            map.put("comments",commentResponse.getData());
            res.add(map);
        }
        return ServerResponse.createBySuccess("获取用户游记信息成功",res);
    }

    @RequestMapping(value = "get_all_travel_note.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Map>> getAllTravelNote() {
        List<Map> res = new ArrayList<>();
        ServerResponse<List<TravelNote>> travelNoteResponse =  iNoteService.getAllTravelNote();
        for(TravelNote travelNote: travelNoteResponse.getData()) {
            ServerResponse<List<NoteImage>> noteImageResponse = iNoteService.getNoteImageByNoteId(travelNote.getId());
            ServerResponse<List<Comment>> commentResponse = iNoteService.getCommentByNoteId(travelNote.getId());
            ServerResponse<User> userServerResponse  = iUserService.getInformation(travelNote.getUserId());
            Map map = new HashMap();
            map.put("id",travelNote.getId());
            map.put("username",userServerResponse.getData().getUsername());
            map.put("noteTitle",travelNote.getNoteTitle());
            map.put("noteBody",travelNote.getNoteBody());
            map.put("location",travelNote.getLocation());
            map.put("browseTimes",travelNote.getBrowseTimes());
            map.put("createTime",travelNote.getCreateTime());
            map.put("updateTime",travelNote.getUpdateTime());
            map.put("images",noteImageResponse.getData());
            map.put("comments",commentResponse.getData());
            res.add(map);
        }
        return ServerResponse.createBySuccess("获取游记信息成功",res);
    }

    @RequestMapping(value = "post_comment.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> postComment(HttpSession session,Integer noteId, String comment) {
        UserWithToken currentUser = (UserWithToken)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        if(comment == null || comment.length() <= 0 || noteId == null) {
            return ServerResponse.createByErrorMsg("参数错误");
        }
        return iNoteService.postComment(noteId,comment,currentUser);
    }

    @RequestMapping(value = "increase_browse_times.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> increaseBrowseTimes(Integer noteId) {

        if(noteId == null) {
            return ServerResponse.createByErrorMsg("参数错误");
        }
        return iNoteService.increaseBrowseTimes(noteId);
    }

    @RequestMapping(value = "get_my_comment.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Comment>> getMyComment(HttpSession session) {
        UserWithToken currentUser = (UserWithToken)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        return iNoteService.getMyComment(currentUser.getUsername());
    }

    @RequestMapping(value = "get_comment.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Comment>> getComment(int noteId) {
        return iNoteService.getComment(noteId);
    }

    @RequestMapping(value = "get_travel_note.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map> getTravelNote(int noteId) {
        ServerResponse<TravelNote> travelNoteResponse =  iNoteService.getTravelNote(noteId);
        TravelNote travelNote = travelNoteResponse.getData();
        ServerResponse<List<NoteImage>> noteImageResponse = iNoteService.getNoteImageByNoteId(travelNote.getId());
        ServerResponse<List<Comment>> commentResponse = iNoteService.getCommentByNoteId(travelNote.getId());
        ServerResponse<User> userServerResponse  = iUserService.getInformation(travelNote.getUserId());
        Map map = new HashMap();
        map.put("id",travelNote.getId());
        map.put("username",userServerResponse.getData().getUsername());
        map.put("noteTitle",travelNote.getNoteTitle());
        map.put("noteBody",travelNote.getNoteBody());
        map.put("location",travelNote.getLocation());
        map.put("browseTimes",travelNote.getBrowseTimes());
        map.put("createTime",travelNote.getCreateTime());
        map.put("updateTime",travelNote.getUpdateTime());
        map.put("images",noteImageResponse.getData());
        map.put("comments",commentResponse.getData());

        return ServerResponse.createBySuccess("获取游记信息成功",map);
    }
}
