package com.smartshopai.service;

import com.smartshopai.domain.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    FileUploadResponse uploadFile(MultipartFile file, String category);

    List<FileUploadResponse> uploadMultipleFiles(List<MultipartFile> files, String category);

    void deleteFile(String fileId);

    FileUploadResponse getFileInfo(String fileId);

    List<FileUploadResponse> getFilesByCategory(String category);

    List<FileUploadResponse> getFilesByUserId(String userId);

    FileUploadResponse updateFileMetadata(String fileId, String description, String category);
}
