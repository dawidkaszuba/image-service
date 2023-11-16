package pl.medrekkaszuba.imageservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Component
public class S3ClientProvider {

    @Value("${s3.accessKey}")
    private String accessKey;

    @Value("${s3.accessSecret}")
    private String accessSecret;

    @Value("${s3.region}")
    private String region;

    public S3Client getS3Client() {
        AwsBasicCredentials credentials = createCredentials();
        return software.amazon.awssdk.services.s3.S3Client.builder()
                .region(getRegion(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    private AwsBasicCredentials createCredentials() {
        return AwsBasicCredentials.create(accessKey,accessSecret);
    }

    private Region getRegion(String region) {
        return Region.of(region);
    }
}
