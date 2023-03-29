package com.linmyx.service_edu.entity.chapter;

import lombok.Data;

@Data
public class VideoVo {
    private String id;
    private String title;
    private Boolean isFree;
    private String videoSourceId;
}
