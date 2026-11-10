package com.jiltsa.admin.files.controller;

import com.jiltsa.admin.files.domain.FileDocumentType;
import com.jiltsa.admin.files.domain.FileOwnerType;
import com.jiltsa.admin.files.domain.FileStatus;
import com.jiltsa.admin.files.persistence.entity.StoredFile;
import com.jiltsa.admin.files.persistence.repository.StoredFileRepository;
import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import com.jiltsa.admin.promotions.persistence.repository.PromotionItemRepository;
import com.jiltsa.admin.promotions.persistence.repository.PromotionRepository;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.entity.SellerProfile;
import com.jiltsa.admin.seller.persistence.repository.SellerProfileRepository;
import com.jiltsa.admin.seller.persistence.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Presigned URLs are signed locally with the test credentials, so they are real; only the calls
 * that would reach the bucket (head, delete) go to a mocked S3Client.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class FileControllerTest {
    private static final String URL = "/jiltsa/api/v1/files";
    private static final String BUCKET = "jiltsa-test-bucket";

    @Autowired private MockMvc mockMvc;
    @Autowired private StoredFileRepository repository;
    @Autowired private PromotionRepository promotionRepository;
    @Autowired private PromotionItemRepository itemRepository;
    @Autowired private SellerRepository sellerRepository;
    @Autowired private SellerProfileRepository sellerProfileRepository;
    @MockitoBean private S3Client s3Client;

    private PromotionItem item(String name) {
        Promotion promotion = promotionRepository.save(new Promotion(name, 1, LocalDate.of(2016, 1, 1), LocalDate.of(2016, 1, 31), true));
        return itemRepository.save(new PromotionItem("K-" + name, "Medicina " + name, 1, 10.0, 5.0, 2.0, promotion.getId(), LocalDate.of(2016, 6, 1)));
    }

    private static String uploadJson(String fileName, String contentType, long size, Long ownerId) {
        return "{\"fileName\":\"" + fileName + "\",\"contentType\":\"" + contentType + "\",\"sizeBytes\":" + size
                + ",\"ownerType\":\"PROMOTION_ITEM\",\"ownerId\":" + ownerId + "}";
    }

    private StoredFile uploaded(PromotionItem item, String fileName) {
        StoredFile file = new StoredFile("promotion-items/" + item.getId() + "/" + fileName, BUCKET, fileName, "image/png", 10L,
                FileOwnerType.PROMOTION_ITEM, item.getId(), null, "tester");
        file.setStatus(FileStatus.UPLOADED);
        file.setUploadedAt(LocalDateTime.now());
        return repository.save(file);
    }

    @Test
    @WithMockUser(username = "ana@jiltsa.com")
    void issuesAPresignedPutAndRecordsThePendingFile() throws Exception {
        PromotionItem item = item("SUBIR");

        String body = mockMvc.perform(post(URL + "/upload-url").contentType(MediaType.APPLICATION_JSON)
                        .content(uploadJson("Foto Frente.JPG", "image/jpeg", 2048, item.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fileId").isNumber())
                .andExpect(jsonPath("$.method").value("PUT"))
                .andExpect(jsonPath("$.objectKey", matchesPattern("promotion-items/" + item.getId() + "/[0-9a-f-]{36}\\.jpg")))
                .andExpect(jsonPath("$.uploadUrl", startsWith("https://" + BUCKET + ".s3.")))
                .andExpect(jsonPath("$.uploadUrl", containsString("X-Amz-Signature=")))
                .andExpect(jsonPath("$.uploadUrl", containsString("X-Amz-Expires=900")))
                .andExpect(jsonPath("$.headers['Content-Type']").value("image/jpeg"))
                .andExpect(jsonPath("$.headers['Content-Length']").value("2048"))
                .andExpect(jsonPath("$.expiresAt").isString())
                .andReturn().getResponse().getContentAsString();
        long fileId = Long.parseLong(body.replaceAll(".*\"fileId\":(\\d+).*", "$1"));

        assertThat(repository.findById(fileId)).get().satisfies(file -> {
            assertThat(file.getStatus()).isEqualTo(FileStatus.PENDING);
            assertThat(file.getBucket()).isEqualTo(BUCKET);
            assertThat(file.getFileName()).isEqualTo("Foto Frente.JPG");
            assertThat(file.getUploadedBy()).isEqualTo("ana@jiltsa.com");
            assertThat(file.getOwnerId()).isEqualTo(item.getId());
        });
        // pending files are not listed and have no download URL yet
        mockMvc.perform(get(URL + "/by-owner/PROMOTION_ITEM/" + item.getId()))
                .andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
        mockMvc.perform(get(URL + "/" + fileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.downloadUrl").doesNotExist());
        mockMvc.perform(get(URL + "/" + fileId + "/download-url")).andExpect(status().isConflict());
    }

    @Test
    @WithMockUser
    void rejectsDisallowedTypesOversizedFilesAndUnknownOwners() throws Exception {
        PromotionItem item = item("RECHAZAR");

        mockMvc.perform(post(URL + "/upload-url").contentType(MediaType.APPLICATION_JSON)
                        .content(uploadJson("virus.exe", "application/octet-stream", 10, item.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", containsString("Content type not allowed")));
        mockMvc.perform(post(URL + "/upload-url").contentType(MediaType.APPLICATION_JSON)
                        .content(uploadJson("grande.png", "image/png", 11L * 1024 * 1024, item.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", containsString("File too large")));
        mockMvc.perform(post(URL + "/upload-url").contentType(MediaType.APPLICATION_JSON)
                        .content(uploadJson("foto.png", "image/png", 10, 999999L)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("PromotionItem not found: 999999"));
        mockMvc.perform(post(URL + "/upload-url").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fileName\":\"\",\"contentType\":\"image/png\",\"sizeBytes\":0,\"ownerType\":\"PROMOTION_ITEM\",\"ownerId\":1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void confirmsAnUploadOnlyWhenTheObjectIsInTheBucket() throws Exception {
        PromotionItem item = item("CONFIRMAR");
        StoredFile file = repository.save(new StoredFile("promotion-items/" + item.getId() + "/pending.png", BUCKET, "pending.png",
                "image/png", 500L, FileOwnerType.PROMOTION_ITEM, item.getId(), null, "tester"));

        when(s3Client.headObject(any(HeadObjectRequest.class))).thenThrow(NoSuchKeyException.builder().build());
        mockMvc.perform(post(URL + "/" + file.getId() + "/confirm"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("not been uploaded")));

        when(s3Client.headObject(any(HeadObjectRequest.class)))
                .thenReturn(HeadObjectResponse.builder().contentLength(512L).eTag("\"abc123\"").build());
        mockMvc.perform(post(URL + "/" + file.getId() + "/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UPLOADED"))
                .andExpect(jsonPath("$.sizeBytes").value(512))
                .andExpect(jsonPath("$.uploadedAt").isString())
                .andExpect(jsonPath("$.downloadUrl", containsString("X-Amz-Signature=")));
        ArgumentCaptor<HeadObjectRequest> head = ArgumentCaptor.forClass(HeadObjectRequest.class);
        verify(s3Client, times(2)).headObject(head.capture());
        assertThat(head.getValue().bucket()).isEqualTo(BUCKET);
        assertThat(head.getValue().key()).isEqualTo(file.getObjectKey());

        // confirming again does not go back to S3
        mockMvc.perform(post(URL + "/" + file.getId() + "/confirm")).andExpect(status().isOk());
        verify(s3Client, times(2)).headObject(any(HeadObjectRequest.class));
        mockMvc.perform(post(URL + "/999999/confirm")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void issuesPresignedDownloadsAndListsByOwnerAndByPromotion() throws Exception {
        PromotionItem item = item("DESCARGAR");
        StoredFile file = uploaded(item, "frente.png");
        uploaded(item, "reverso.png");

        mockMvc.perform(get(URL + "/" + file.getId() + "/download-url"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("frente.png"))
                .andExpect(jsonPath("$.downloadUrl", startsWith("https://" + BUCKET + ".s3.")))
                .andExpect(jsonPath("$.downloadUrl", containsString("/promotion-items/" + item.getId() + "/frente.png?")))
                .andExpect(jsonPath("$.downloadUrl", containsString("X-Amz-Expires=3600")))
                .andExpect(jsonPath("$.downloadUrl", containsString("response-content-disposition=inline")));

        mockMvc.perform(get(URL + "/by-owner/PROMOTION_ITEM/" + item.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].fileName", containsInAnyOrder("frente.png", "reverso.png")))
                .andExpect(jsonPath("$[0].downloadUrl", containsString("X-Amz-Signature=")));

        mockMvc.perform(get(URL + "/by-promotion/" + item.getPromotionId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promotionName").value("DESCARGAR"))
                .andExpect(jsonPath("$.files").value(2))
                .andExpect(jsonPath("$.items[0].itemId").value(item.getId()))
                .andExpect(jsonPath("$.items[0].key").value("K-DESCARGAR"))
                .andExpect(jsonPath("$.items[0].files.length()").value(2));
        mockMvc.perform(get(URL + "/by-promotion/999999")).andExpect(status().isNotFound());
        mockMvc.perform(get(URL + "/by-owner/UNKNOWN/1")).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void sellerProfileDocumentsCarryTheirDocumentType() throws Exception {
        Seller seller = sellerRepository.save(new Seller("Vendedora Documentos", 1, true));
        SellerProfile profile = new SellerProfile();
        profile.setSellerId(seller.getId());
        profile.setFullName("Vendedora Documentos");
        profile.setBirthDate(LocalDate.of(1990, 1, 1));
        profile.setPhone("8711234567");
        profile.setEmergencyContactName("Contacto");
        profile.setEmergencyContactPhone("8717654321");
        profile.setHireDate(LocalDate.of(2024, 1, 1));
        profile.setPosition("Vendedora");
        profile.setSalary(8000.0);
        profile.setPrivacyNoticeAccepted(true);
        profile.setPrivacyNoticeAcceptedAt(LocalDateTime.now());
        profile.setRegisteredBy("tester");
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());
        profile = sellerProfileRepository.save(profile);
        String base = "{\"fileName\":\"ine.jpg\",\"contentType\":\"image/jpeg\",\"sizeBytes\":100,\"ownerType\":\"SELLER_PROFILE\",\"ownerId\":" + profile.getId();

        mockMvc.perform(post(URL + "/upload-url").contentType(MediaType.APPLICATION_JSON).content(base + "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", containsString("documentType is required")));
        mockMvc.perform(post(URL + "/upload-url").contentType(MediaType.APPLICATION_JSON).content(base + ",\"documentType\":\"INE_FRONT\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.objectKey", startsWith("seller-profiles/" + profile.getId() + "/")));
        mockMvc.perform(post(URL + "/upload-url").contentType(MediaType.APPLICATION_JSON)
                        .content(base.replace("\"ownerId\":" + profile.getId(), "\"ownerId\":999999") + ",\"documentType\":\"INE_FRONT\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("SellerProfile not found: 999999"));

        StoredFile proof = new StoredFile("seller-profiles/" + profile.getId() + "/proof.pdf", BUCKET, "proof.pdf", "application/pdf", 10L,
                FileOwnerType.SELLER_PROFILE, (long) profile.getId(), FileDocumentType.ADDRESS_PROOF, "tester");
        proof.setStatus(FileStatus.UPLOADED);
        proof.setUploadedAt(LocalDateTime.now());
        repository.save(proof);
        mockMvc.perform(get(URL + "/by-owner/SELLER_PROFILE/" + profile.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].documentType").value("ADDRESS_PROOF"))
                .andExpect(jsonPath("$[0].downloadUrl", containsString("X-Amz-Signature=")));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deletesTheObjectAndTheRecord() throws Exception {
        PromotionItem item = item("BORRAR");
        StoredFile file = uploaded(item, "borrar.png");
        when(s3Client.deleteObject(any(DeleteObjectRequest.class))).thenReturn(DeleteObjectResponse.builder().build());

        mockMvc.perform(delete(URL + "/" + file.getId())).andExpect(status().isNoContent());
        ArgumentCaptor<DeleteObjectRequest> deleted = ArgumentCaptor.forClass(DeleteObjectRequest.class);
        verify(s3Client).deleteObject(deleted.capture());
        assertThat(deleted.getValue().key()).isEqualTo(file.getObjectKey());
        assertThat(repository.findById(file.getId())).isEmpty();
        mockMvc.perform(delete(URL + "/" + file.getId())).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void onlyAdminDeletes() throws Exception {
        mockMvc.perform(delete(URL + "/999999")).andExpect(status().isForbidden());
    }
}
