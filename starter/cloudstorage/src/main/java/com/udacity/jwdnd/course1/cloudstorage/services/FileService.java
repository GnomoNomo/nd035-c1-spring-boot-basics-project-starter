package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveNewFile(MultipartFile file, Integer userId) throws IOException {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        InputStream input = file.getInputStream();
        byte[] fileData = file.getBytes();
        long fileSize = file.getSize();
        return fileMapper.insert(new File(null, fileName, contentType, fileSize, userId, fileData));
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getFiles(userId);
    }

    public File getFile(String fileName, Integer userId){
        return fileMapper.getFile(fileName, userId);
    }

    public void deleteFile(File file) {
        fileMapper.deleteFile(file);
    }
}
