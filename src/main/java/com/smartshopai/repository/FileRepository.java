package com.smartshopai.repository;

import com.smartshopai.domain.entity.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends MongoRepository<FileEntity, String> {

    List<FileEntity> findByCategory(String category);

    List<FileEntity> findByUploadedBy(String uploadedBy);

    List<FileEntity> findByFileType(String fileType);

    List<FileEntity> findByMimeType(String mimeType);

    List<FileEntity> findByFileSizeBetween(long minSize, long maxSize);

    List<FileEntity> findByOriginalFileNameContainingIgnoreCase(String filename);

    long countByCategory(String category);

    long countByUploadedBy(String uploadedBy);

    List<FileEntity> findByCategoryAndUploadedBy(String category, String uploadedBy);
}
