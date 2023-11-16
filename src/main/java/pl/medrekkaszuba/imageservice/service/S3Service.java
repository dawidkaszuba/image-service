package pl.medrekkaszuba.imageservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.medrekkaszuba.imageservice.exception.ProcessingImageException;
import pl.medrekkaszuba.imageservice.util.S3ClientProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@Service
public class S3Service {

    @Value("${s3.bucket}")
    private String bucket;

    @Value("${s3.bucketPrefix}")
    private String bucketPrefix;

    private final S3ClientProvider s3ClientProvider;

    public S3Service(S3ClientProvider s3ClientProvider) {
        this.s3ClientProvider = s3ClientProvider;
    }

    public String uploadImage(byte[] bytes, String key, String contentType) {
        try (S3Client s3Client = s3ClientProvider.getS3Client()) {
            s3Client.putObject(PutObjectRequest
                    .builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build(), RequestBody.fromBytes(bytes));
        } catch (S3Exception e) {
            log.error(String.format("Error during uploading an image: %s", e.getMessage()));
            throw new ProcessingImageException("S3 upload error", e);
        }
        return bucketPrefix + key;
    }
}
