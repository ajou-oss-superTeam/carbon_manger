package com.oss.carbonadministrator.dto.request.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {

    private String email;

    private int year;

    private int month;

    private String image;

    private String uri;

}
