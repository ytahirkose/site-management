package com.smartshopai.service.impl;

import com.smartshopai.domain.dto.FileUploadResponse;
import com.smartshopai.domain.entity.FileEntity;
import com.smartshopai.service.FileService;
import com.smartshopai.exception.ResourceNotFoundException;
import com.smartshopai.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    
    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public FileUploadResponse uploadFile(MultipartFile file, String category) {
        log.info("Uploading file: {} with category: {}", file.getOriginalFilename(), category);

        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);
            String storedFileName = UUID.randomUUID().toString() + fileExtension;
            
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            Path filePath = uploadPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), filePath);
            
            FileEntity fileEntity = FileEntity.builder()
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .fileUrl("/files/" + storedFileName) // TODO: Replace with cloud storage URL when configured
                    .fileType(fileExtension)
                    .fileSize(file.getSize())
                    .category(category)
                    .mimeType(file.getContentType())
                    .uploadedAt(LocalDateTime.now())
                    .build();
            
            FileEntity savedFile = fileRepository.save(fileEntity);
            log.info("File uploaded successfully with ID: {}", savedFile.getId());
            
            return mapToFileUploadResponse(savedFile);
            
        } catch (IOException e) {
            log.error("Error uploading file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public List<FileUploadResponse> uploadMultipleFiles(List<MultipartFile> files, String category) {
        log.info("Uploading {} files with category: {}", files.size(), category);
        
        return files.stream()
                .map(file -> uploadFile(file, category))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(String fileId) {
        log.info("Deleting file: {}", fileId);
        
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with ID: " + fileId));
        
        try {
            Path filePath = Paths.get(UPLOAD_DIR, fileEntity.getStoredFileName());
            Files.deleteIfExists(filePath);
            
            fileRepository.deleteById(fileId);
            log.info("File deleted successfully: {}", fileId);
            
        } catch (IOException e) {
            log.error("Error deleting file from storage: {}", fileId, e);
            throw new RuntimeException("Failed to delete file from storage: " + fileId, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FileUploadResponse getFileInfo(String fileId) {
        log.debug("Getting file info for ID: {}", fileId);
        
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with ID: " + fileId));
        
        return mapToFileUploadResponse(fileEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileUploadResponse> getFilesByCategory(String category) {
        log.debug("Getting files for category: {}", category);
        
        List<FileEntity> files = fileRepository.findByCategory(category);
        return files.stream()
                .map(this::mapToFileUploadResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileUploadResponse> getFilesByUserId(String userId) {
        log.debug("Getting files for user: {}", userId);
        
        List<FileEntity> files = fileRepository.findByUploadedBy(userId);
        return files.stream()
                .map(this::mapToFileUploadResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FileUploadResponse updateFileMetadata(String fileId, String description, String category) {
        log.info("Updating metadata for file: {}", fileId);
        
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with ID: " + fileId));
        
        fileEntity.setDescription(description);
        fileEntity.setCategory(category);
        fileEntity.setUpdatedAt(LocalDateTime.now());
        
        FileEntity updatedFile = fileRepository.save(fileEntity);
        log.info("File metadata updated successfully: {}", fileId);
        
        return mapToFileUploadResponse(updatedFile);
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    private FileUploadResponse mapToFileUploadResponse(FileEntity fileEntity) {
        return FileUploadResponse.builder()
                .id(fileEntity.getId())
                .originalFileName(fileEntity.getOriginalFileName())
                .storedFileName(fileEntity.getStoredFileName())
                .fileUrl(fileEntity.getFileUrl())
                .fileType(fileEntity.getFileType())
                .fileSize(fileEntity.getFileSize())
                .category(fileEntity.getCategory())
                .description(fileEntity.getDescription())
                .uploadedBy(fileEntity.getUploadedBy())
                .uploadedAt(fileEntity.getUploadedAt())
                .mimeType(fileEntity.getMimeType())
                .build();
    }
}
