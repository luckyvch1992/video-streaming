package com.video.storage.utils;

import com.video.storage.entity.MediaFile;
import org.springframework.web.multipart.MultipartFile;

public class MediaFileUtils {

    public static MediaFile buildMediaFile(MultipartFile multipartFile, String filePath) {
        return MediaFile.builder()
                .path(filePath)
                .fileSize(multipartFile.getSize())
                .mimeType(multipartFile.getContentType())
                .build();
    }
}
