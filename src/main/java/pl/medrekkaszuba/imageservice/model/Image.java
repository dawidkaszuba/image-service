package pl.medrekkaszuba.imageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image {
    private String url;
    private String newsItemId;
}
