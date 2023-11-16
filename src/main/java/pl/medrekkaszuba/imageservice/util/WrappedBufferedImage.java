package pl.medrekkaszuba.imageservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Getter
@Setter
@AllArgsConstructor
public class WrappedBufferedImage {
    private ImageFileFormat format;
    private BufferedImage image;
}
