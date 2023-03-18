package com.video.storage.utils;

import com.video.storage.entity.MediaFile;
import org.springframework.http.codec.multipart.FilePart;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class MediaFileUtils {

    public static MediaFile buildMediaFile(FilePart filePart, String filePath) {
        return MediaFile.builder()
                .path(filePath)
                .fileSize(filePart.headers().getContentLength())
                .mimeType(requireNonNull(filePart.headers().getContentType()).toString())
                .build();
    }
}
