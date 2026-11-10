package com.jiltsa.admin.files.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

/** One sync client (head/delete) and one presigner (upload/download URLs), configured alike. */
@Configuration
public class S3Config {

    @Bean
    S3Client s3Client(StorageProperties properties) {
        var builder = S3Client.builder()
                .region(Region.of(properties.region()))
                .credentialsProvider(credentials(properties))
                .serviceConfiguration(serviceConfiguration(properties));
        if (properties.hasEndpoint()) builder.endpointOverride(URI.create(properties.endpoint()));
        return builder.build();
    }

    @Bean
    S3Presigner s3Presigner(StorageProperties properties) {
        var builder = S3Presigner.builder()
                .region(Region.of(properties.region()))
                .credentialsProvider(credentials(properties))
                .serviceConfiguration(serviceConfiguration(properties));
        if (properties.hasEndpoint()) builder.endpointOverride(URI.create(properties.endpoint()));
        return builder.build();
    }

    private static AwsCredentialsProvider credentials(StorageProperties properties) {
        return properties.hasStaticCredentials()
                ? StaticCredentialsProvider.create(AwsBasicCredentials.create(properties.accessKey(), properties.secretKey()))
                : DefaultCredentialsProvider.builder().build();
    }

    /** S3-compatible servers (MinIO) usually want path-style URLs; AWS gets virtual-hosted ones. */
    private static S3Configuration serviceConfiguration(StorageProperties properties) {
        return S3Configuration.builder().pathStyleAccessEnabled(properties.hasEndpoint()).build();
    }
}
