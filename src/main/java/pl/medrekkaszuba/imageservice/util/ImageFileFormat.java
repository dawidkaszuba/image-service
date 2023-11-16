package pl.medrekkaszuba.imageservice.util;

import lombok.Getter;

@Getter
public enum ImageFileFormat {

    JPG("jpg"),
    PNG("png");


    private final String format;

    ImageFileFormat(String format) {
        this.format = format;
    }

}
