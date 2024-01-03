package pl.medrekkaszuba.imageservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.medrekkaszuba.imageservice.exception.ProcessingImageException;
import pl.medrekkaszuba.imageservice.model.Image;
import pl.medrekkaszuba.imageservice.publisher.KafkaPublisherService;
import pl.medrekkaszuba.imageservice.util.ImageFileFormat;
import pl.medrekkaszuba.imageservice.util.ImageUtils;
import pl.medrekkaszuba.imageservice.util.WrappedBufferedImage;

import java.io.IOException;
import java.time.OffsetDateTime;

@Slf4j
@Service
public class ImageService {

    private final S3Service s3Service;
    private final KafkaPublisherService kafkaPublisherService;

    public ImageService(S3Service s3Service, KafkaPublisherService kafkaPublisherService) {
        this.s3Service = s3Service;
        this.kafkaPublisherService = kafkaPublisherService;
    }


    public void processImage(Image image) {

        WrappedBufferedImage wrappedBufferedImage = ImageUtils.downloadImage(image.getUrl());

        String key = createImageKey(image.getNewsItemId(), wrappedBufferedImage.getFormat());
        String url;
        byte[] bytes = null;
        try {

           if (wrappedBufferedImage.getImage() != null) {
               bytes = ImageUtils.getBytesFromBufferedImage(wrappedBufferedImage.getImage(), wrappedBufferedImage.getFormat());
           }

           if (bytes != null) {
               url = s3Service.uploadImage(bytes, key, getContentType(wrappedBufferedImage));
           } else {
               return;
           }

        } catch (IOException e) {
            log.error("Error during uploading image to S3 bucket: {}", e.getMessage());
            throw new ProcessingImageException("Error during uploading image to S3 bucket", e);
        }
        kafkaPublisherService.sendProcessedImage(new Image(url, image.getNewsItemId()));
    }

    private String createImageKey(String newsId, ImageFileFormat format) {
        return createBucketDirName() + createImageName(newsId, format);
    }

    private String createImageName(String newsId, ImageFileFormat format) {
        return newsId + "." + format.name().toLowerCase();
    }

    private String createBucketDirName() {
        OffsetDateTime current =  OffsetDateTime.now();
        return current.getYear() + "_" + current.getMonthValue() + "_" + current.getDayOfMonth() + "/";
    }

    private String getContentType(WrappedBufferedImage imageWithType) {
        String contentType;
        if (ImageFileFormat.JPG.equals(imageWithType.getFormat())) {
            contentType = "image/jpg";
        } else {
            contentType = "image/png";
        }
        return contentType;
    }
}
