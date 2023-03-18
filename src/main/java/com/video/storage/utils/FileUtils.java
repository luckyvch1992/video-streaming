package com.video.storage.utils;

import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.util.UUID;

import static java.lang.String.format;

public class FileUtils {

    public static final String FILE_NAME_FORMAT = "%s.%s";
    public static final String FILE_PATH_FORMAT = "/%s/%s";

    public static String generateFilePath(String mimeType) {
        return format(FILE_PATH_FORMAT, UUID.randomUUID(), generateFileName(mimeType));
    }

    public static String generateFileName(String mimeType) {
        return format(FILE_NAME_FORMAT, UUID.randomUUID(), getExtensionFromMimeType(mimeType));
    }

    private static String getExtensionFromMimeType(String mimeType) {
        try {
            return MimeTypes.getDefaultMimeTypes().getRegisteredMimeType(mimeType).getExtension();
        } catch (MimeTypeException e) {
            throw new RuntimeException("Mime type could not be detected and extension could not be fetched");
        }
    }
}
