package com.smartshopai.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "files")
public class FileEntity {

    @Id
    private String id;

    @NotBlank(message = "Original file name is required")
    @Field("original_file_name")
    private String originalFileName;

    @NotBlank(message = "Stored file name is required")
    @Field("stored_file_name")
    private String storedFileName;

    @NotBlank(message = "File URL is required")
    @Field("file_url")
    private String fileUrl;

    @NotBlank(message = "File type is required")
    @Field("file_type")
    private String fileType;

    @NotNull(message = "File size is required")
    @Field("file_size")
    private Long fileSize;

    @Field("uploaded_by")
    @Indexed
    private String uploadedBy;

    @Field("uploaded_by_name")
    private String uploadedByName;

    @Field("category")
    private String category;

    @Field("description")
    private String description;

    @Field("mime_type")
    private String mimeType;

    @Field("related_entity_type")
    private EntityType relatedEntityType;

    @Field("related_entity_id")
    private String relatedEntityId;

    @Field("storage_provider")
    private StorageProvider storageProvider;

    @Field("is_public")
    @Builder.Default
    private boolean isPublic = false;

    @Field("expires_at")
    private LocalDateTime expiresAt;

    @Field("metadata")
    private String metadata;

    @Field("file_hash")
    private String fileHash;

    @Field("virus_scan_status")
    @Builder.Default
    private VirusScanStatus virusScanStatus = VirusScanStatus.PENDING;

    @Field("virus_scan_date")
    private LocalDateTime virusScanDate;

    @Field("compression_ratio")
    private Double compressionRatio;

    @Field("thumbnail_url")
    private String thumbnailUrl;

    @Field("preview_url")
    private String previewUrl;

    @Field("download_count")
    @Builder.Default
    private Integer downloadCount = 0;

    @Field("last_downloaded")
    private LocalDateTime lastDownloaded;

    @Field("access_logs")
    private String[] accessLogs;

    @Field("site_id")
    @NotBlank
    private String siteId; // Site ID for multi-tenant

    @Field("uploaded_at")
    private LocalDateTime uploadedAt;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    public enum EntityType {
        PAYMENT_RECEIPT, BANK_RECEIPT, EFT_RECEIPT, ISSUE_PHOTO, ANNOUNCEMENT_ATTACHMENT, USER_PROFILE
    }

    public enum StorageProvider {
        CLOUDINARY, FIREBASE, SUPABASE, LOCAL
    }

    public enum VirusScanStatus {
        PENDING, SCANNING, CLEAN, INFECTED, ERROR
    }
}
