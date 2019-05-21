package com.travelassistant.service;

import com.travelassistant.common.ServerResponse;
import org.apache.commons.fileupload.FileItem;

public interface IFileService {

    ServerResponse<String> upload(FileItem fileItem, String path, int noteId);
}

