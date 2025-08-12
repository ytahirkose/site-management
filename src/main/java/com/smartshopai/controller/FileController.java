package com.smartshopai.controller;

import com.smartshopai.domain.dto.FileUploadResponse;
import com.smartshopai.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "File Management", description = "APIs for file upload and management")
@SecurityRequirement(name = "bearerAuth")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "Upload single file", description = "Upload a single file with category")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") String category) {
        log.info("Uploading file: {} with category: {}", file.getOriginalFilename(), category);
        FileUploadResponse response = fileService.uploadFile(file, category);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/upload/multiple")
    @Operation(summary = "Upload multiple files", description = "Upload multiple files with category")
    public ResponseEntity<List<FileUploadResponse>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("category") String category) {
        log.info("Uploading {} files with category: {}", files.size(), category);
        List<FileUploadResponse> responses = fileService.uploadMultipleFiles(files, category);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/{fileId}")
    @Operation(summary = "Get file information", description = "Get file information by ID")
    public ResponseEntity<FileUploadResponse> getFileInfo(@PathVariable String fileId) {
        log.debug("Getting file info for ID: {}", fileId);
        FileUploadResponse fileInfo = fileService.getFileInfo(fileId);
        return ResponseEntity.ok(fileInfo);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get files by category", description = "Get all files in a specific category")
    public ResponseEntity<List<FileUploadResponse>> getFilesByCategory(@PathVariable String category) {
        log.debug("Getting files for category: {}", category);
        List<FileUploadResponse> files = fileService.getFilesByCategory(category);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get files by user ID", description = "Get all files uploaded by a specific user")
    public ResponseEntity<List<FileUploadResponse>> getFilesByUserId(@PathVariable String userId) {
        log.debug("Getting files for user: {}", userId);
        List<FileUploadResponse> files = fileService.getFilesByUserId(userId);
        return ResponseEntity.ok(files);
    }

    @PutMapping("/{fileId}/metadata")
    @Operation(summary = "Update file metadata", description = "Update file description and category")
    public ResponseEntity<FileUploadResponse> updateFileMetadata(
            @PathVariable String fileId,
            @RequestParam("description") String description,
            @RequestParam("category") String category) {
        log.info("Updating metadata for file: {}", fileId);
        FileUploadResponse updatedFile = fileService.updateFileMetadata(fileId, description, category);
        return ResponseEntity.ok(updatedFile);
    }

    @DeleteMapping("/{fileId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete file", description = "Admin only - Delete a file")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileId) {
        log.info("Deleting file: {}", fileId);
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload/payment-receipt")
    @Operation(summary = "Upload payment receipt", description = "Upload payment receipt document (bank receipt, EFT/havale screenshot)")
    public ResponseEntity<FileUploadResponse> uploadPaymentReceipt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("paymentId") String paymentId,
            @RequestParam(value = "description", required = false) String description) {
        log.info("Uploading payment receipt for payment: {}", paymentId);
        FileUploadResponse response = fileService.uploadFile(file, "PAYMENT_RECEIPT");
        
        if (description != null) {
            response = fileService.updateFileMetadata(response.getId(), description, "PAYMENT_RECEIPT");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/upload/bank-receipt")
    @Operation(summary = "Upload bank receipt", description = "Upload bank receipt document for payment verification")
    public ResponseEntity<FileUploadResponse> uploadBankReceipt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("paymentId") String paymentId,
            @RequestParam(value = "description", required = false) String description) {
        log.info("Uploading bank receipt for payment: {}", paymentId);
        FileUploadResponse response = fileService.uploadFile(file, "BANK_RECEIPT");
        
        if (description != null) {
            response = fileService.updateFileMetadata(response.getId(), description, "BANK_RECEIPT");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/upload/eft-receipt")
    @Operation(summary = "Upload EFT receipt", description = "Upload EFT/havale receipt screenshot for payment verification")
    public ResponseEntity<FileUploadResponse> uploadEftReceipt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("paymentId") String paymentId,
            @RequestParam(value = "description", required = false) String description) {
        log.info("Uploading EFT receipt for payment: {}", paymentId);
        FileUploadResponse response = fileService.uploadFile(file, "EFT_RECEIPT");
        
        if (description != null) {
            response = fileService.updateFileMetadata(response.getId(), description, "EFT_RECEIPT");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/upload/issue-photo")
    @Operation(summary = "Upload issue photo", description = "Upload photo for issue report (camera or file selection)")
    public ResponseEntity<FileUploadResponse> uploadIssuePhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("issueId") String issueId,
            @RequestParam(value = "description", required = false) String description) {
        log.info("Uploading photo for issue: {}", issueId);
        FileUploadResponse response = fileService.uploadFile(file, "ISSUE_PHOTO");
        
        if (description != null) {
            response = fileService.updateFileMetadata(response.getId(), description, "ISSUE_PHOTO");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/upload/camera-photo")
    @Operation(summary = "Upload camera photo", description = "Upload photo taken with camera for any purpose")
    public ResponseEntity<FileUploadResponse> uploadCameraPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") String category,
            @RequestParam(value = "description", required = false) String description) {
        log.info("Uploading camera photo for category: {}", category);
        FileUploadResponse response = fileService.uploadFile(file, category);
        
        if (description != null) {
            response = fileService.updateFileMetadata(response.getId(), description, category);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/upload/file-selection")
    @Operation(summary = "Upload selected file", description = "Upload file selected from device storage for any purpose")
    public ResponseEntity<FileUploadResponse> uploadSelectedFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") String category,
            @RequestParam(value = "description", required = false) String description) {
        log.info("Uploading selected file for category: {}", category);
        FileUploadResponse response = fileService.uploadFile(file, category);
        
        if (description != null) {
            response = fileService.updateFileMetadata(response.getId(), description, category);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
