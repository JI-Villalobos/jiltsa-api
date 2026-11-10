package com.jiltsa.admin.files.controller;

import com.jiltsa.admin.files.domain.FileOwnerType;
import com.jiltsa.admin.files.domain.dto.*;
import com.jiltsa.admin.files.domain.service.StoredFileService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Files never pass through the API: the client PUTs to and GETs from the bucket with presigned
 * URLs, and the API keeps the record of each object and who it belongs to.
 */
@RestController
@RequestMapping("jiltsa/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final StoredFileService service;

    @Operation(summary = "Request a presigned URL to upload a file",
            description = "Records the file as PENDING for its owner and returns a PUT URL; send the body with exactly the returned headers, then confirm the upload.")
    @ApiResponse(responseCode = "201", description = "Upload URL issued", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", description = "Content type not allowed or file too large")
    @ApiResponse(responseCode = "404", description = "The owner entity does not exist")
    @PostMapping("/upload-url")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadUrlDto requestUpload(@Valid @RequestBody UploadRequestDto request) {
        return service.requestUpload(request);
    }

    @Operation(summary = "Confirm that a file was uploaded", description = "Checks the object is in the bucket and marks the record UPLOADED. Idempotent.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No file with that id")
    @ApiResponse(responseCode = "409", description = "The object is not in the bucket yet")
    @PostMapping("/{fileId}/confirm")
    public StoredFileDto confirmUpload(@Parameter(description = "File id") @PathVariable("fileId") Long fileId) {
        return service.confirmUpload(fileId);
    }

    @Operation(summary = "Get a file record", description = "Includes a presigned download URL once the file is uploaded.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No file with that id")
    @GetMapping("/{fileId}")
    public StoredFileDto getFile(@Parameter(description = "File id") @PathVariable("fileId") Long fileId) {
        return service.getFile(fileId);
    }

    @Operation(summary = "Get a presigned URL to download a file")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No file with that id")
    @ApiResponse(responseCode = "409", description = "The file is not uploaded yet")
    @GetMapping("/{fileId}/download-url")
    public DownloadUrlDto getDownloadUrl(@Parameter(description = "File id") @PathVariable("fileId") Long fileId) {
        return service.getDownloadUrl(fileId);
    }

    @Operation(summary = "List the uploaded files of an entity", description = "Newest first, each with a presigned download URL.")
    @GetMapping("/by-owner/{ownerType}/{ownerId}")
    public List<StoredFileDto> getFilesByOwner(
            @Parameter(description = "Owner entity type") @PathVariable("ownerType") FileOwnerType ownerType,
            @Parameter(description = "Owner entity id") @PathVariable("ownerId") Long ownerId) {
        return service.getFilesByOwner(ownerType, ownerId);
    }

    @Operation(summary = "List the uploaded files of every item of a promotion", description = "Medicine by medicine, each file with a presigned download URL.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No promotion with that id")
    @GetMapping("/by-promotion/{promotionId}")
    public PromotionFilesDto getFilesByPromotion(
            @Parameter(description = "Promotion id") @PathVariable("promotionId") Integer promotionId) {
        return service.getFilesByPromotion(promotionId);
    }

    @Operation(summary = "Delete a file", description = "Removes the object from the bucket and its record.")
    @ApiResponse(responseCode = "204", description = "Deleted")
    @ApiResponse(responseCode = "404", description = "No file with that id")
    @AdminOnly
    @DeleteMapping("/{fileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@Parameter(description = "File id") @PathVariable("fileId") Long fileId) {
        service.deleteFile(fileId);
    }
}
