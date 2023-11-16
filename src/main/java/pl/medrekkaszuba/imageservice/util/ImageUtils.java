package pl.medrekkaszuba.imageservice.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import pl.medrekkaszuba.imageservice.exception.ProcessingImageException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

@Slf4j
@UtilityClass
public class ImageUtils {

    public static WrappedBufferedImage downloadImage(String fileUrl) {
        BufferedImage image = null;
        ImageFileFormat format = null;
        try {
            URL url = new URL(fileUrl);
            image = ImageIO.read(url);
            format = getImageFormat(getBytesFromUrl(fileUrl));
        } catch (IOException e) {
            log.error("Error during downloading an image: {}", e.getMessage());
        }
        return new WrappedBufferedImage(format, image);
    }

    public static ImageFileFormat getImageFormat(byte[] content) {
        String formatName = null;
        ImageFileFormat imageFileFormat = ImageFileFormat.JPG;
        ImageInputStream imgStream = null;
        try {
            imgStream = ImageIO.createImageInputStream(getInputStream(content));
        } catch (IOException e) {
            log.error("Error during creating image input stream: {}", e.getMessage());
        }
        Iterator<ImageReader> iter = ImageIO.getImageReaders(imgStream);
        while (iter.hasNext()) {
            ImageReader reader = iter.next();
            reader.setInput(imgStream);
            try {
                formatName = reader.getFormatName();
            } catch (IOException e) {
                log.error("Error during image format extracting.");
            }
            if ("png".equals(formatName)) {
                imageFileFormat  = ImageFileFormat.PNG;
            } else {
                imageFileFormat  = ImageFileFormat.JPG;
            }
        }
        return imageFileFormat;
    }

    private InputStream getInputStream(byte[] content) {
        InputStream inputStream = new ByteArrayInputStream(content);
        return new BufferedInputStream(inputStream);
    }

    private byte[] getBytesFromUrl(String url) throws IOException {
        URL obj = new URL(url);
        byte[] bytes;
        try(InputStream inputStream = obj.openStream()) {
            bytes = inputStream.readAllBytes();
        } catch (IOException e) {
            log.error("Error during downloading an image bytes from url: {}", e.getMessage());
            throw new ProcessingImageException("Error during downloading an image bytes from url", e);
        }
        return bytes;
    }

    public byte[] getBytesFromBufferedImage(BufferedImage image, ImageFileFormat format) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format.name(), byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
