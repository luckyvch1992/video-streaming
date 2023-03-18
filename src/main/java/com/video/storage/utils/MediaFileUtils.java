package com.video.storage.utils;

import com.video.storage.entity.MediaFile;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;

import static java.util.Objects.requireNonNull;

public class MediaFileUtils {

    public static MediaFile buildMediaFile(FilePart filePart, String filePath) {
        return MediaFile.builder()
                .path(filePath)
                .fileSize(
                        filePart.content()
                                .map(DataBuffer::readableByteCount)
                                .reduce(Integer::sum)
                                .map(Integer::longValue)
                                .block()
                )
                .mimeType(requireNonNull(filePart.headers().getContentType()).toString())
                .build();
    }
}
