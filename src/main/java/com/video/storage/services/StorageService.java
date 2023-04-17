package com.video.storage.services;

import com.video.storage.entity.MediaFile;
import com.video.storage.repository.StorageRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.video.storage.utils.FileUtils.generateFilePath;
import static com.video.storage.utils.MediaFileUtils.buildMediaFile;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StorageService {

    @Value("${minio.bucket-name}")
    private String videosBucketName;

    private final MinioClient minioClient;
    private final StorageRepository storageRepository;

    public Mono<String> upload(Mono<FilePart> filePartMono) {
        return filePartMono.flatMap(filePart -> DataBufferUtils
                .join(filePart.content())
                .map(DataBuffer::asInputStream)
                .map(inputStream -> uploadAndSave(filePart, inputStream))
        );
    }

    public List<MediaFile> findAll() {
        return StreamSupport.stream(storageRepository.findAll().spliterator(), false)
                .collect(toList());
    }

    private String uploadAndSave(FilePart filePart, InputStream inputStream) {
        String mimeType = requireNonNull(filePart.headers().getContentType()).toString();
        var filePath = generateFilePath(mimeType);
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(videosBucketName)
                            .object(filePath)
                            .contentType(mimeType)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());
            var mediaFile = buildMediaFile(filePart, filePath);
            storageRepository.save(mediaFile);
            return mediaFile.getId();
        } catch (Exception e) {
            throw new RuntimeException(format("File is not saved because of error: %s", e.getMessage()));
        }
    }
}
