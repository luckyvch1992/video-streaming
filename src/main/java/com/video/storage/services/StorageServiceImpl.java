package com.video.storage.services;

import com.video.storage.entity.MediaFile;
import com.video.storage.repository.StorageRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.video.storage.utils.FileUtils.generateFilePath;
import static com.video.storage.utils.MediaFileUtils.buildMediaFile;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StorageServiceImpl {

    @Value("${minio.bucket-name}")
    private String videosBucketName;

    private final MinioClient minioClient;
    private final StorageRepository storageRepository;

    public String upload(MultipartFile file) {
        var objectName = generateFilePath(file.getContentType());
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(videosBucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(format("File is not saved because of error: ", e.getMessage()));
        }
        var mediaFile = buildMediaFile(file, objectName);
        storageRepository.save(mediaFile);
        return mediaFile.getId();
    }

    public List<MediaFile> findAll() {
        return null;
    }
}
