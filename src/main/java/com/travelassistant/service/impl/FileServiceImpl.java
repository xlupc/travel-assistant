package com.travelassistant.service.impl;

import com.travelassistant.common.ServerResponse;
import com.travelassistant.dao.NoteImageMapper;
import com.travelassistant.pojo.NoteImage;
import com.travelassistant.service.IFileService;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    @Autowired
    private NoteImageMapper noteImageMapper;

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    public ServerResponse<String> upload(FileItem fileItem, String path, int noteId){
        String fileName = fileItem.getName();
        System.out.println(fileName);
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadFileName);
        //.上传内容
        try {
            InputStream is = null;    //获得输入流，
            is = fileItem.getInputStream();
            FileOutputStream out = new FileOutputStream(targetFile);
            byte[] buf = new byte[1024];
            int len = -1;
            while( (len = is.read(buf)) != -1){
                out.write(buf, 0, len);
            }

            //关闭
            out.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把图片的相对路径保存至数据库
        String sqlPath = "/images/"+targetFile.getName();
        NoteImage noteImage = new NoteImage();
        noteImage.setImgPath(sqlPath);
        noteImage.setNoteId(noteId);
        int resultCount = noteImageMapper.insert(noteImage);
        if(resultCount == 0){
            return ServerResponse.createByErrorMsg("发布失败");
        }
        return ServerResponse.createBySuccessMsg("发布成功");
    }

}
