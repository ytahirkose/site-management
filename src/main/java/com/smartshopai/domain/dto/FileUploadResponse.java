package com.smartshopai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {

    private String id;
    private String originalFileName;
    private String storedFileName;
    private String fileUrl;
    private String fileType;
    private long fileSize;
    private String category;
    private String description;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
    private String mimeType;
}
