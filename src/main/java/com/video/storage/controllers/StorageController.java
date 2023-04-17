package com.video.storage.controllers;

import com.video.storage.dto.MediaFileDTO;
import com.video.storage.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("storage")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StorageController {

    private final StorageService storageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> upload(@RequestPart("file") Mono<FilePart> filePartMono) {
        return storageService.upload(filePartMono);
    }

    @GetMapping(value = "findAll")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<MediaFileDTO> findAll() {
        var mediaFiles = storageService.findAll();
        return mediaFiles.stream()
                .map(mediaFile -> MediaFileDTO.builder()
                        .id(mediaFile.getId())
                        .mimeType(mediaFile.getMimeType())
                        .fileSize(mediaFile.getFileSize())
                        .fileName(mediaFile.getFileName())
                        .build()
                )
                .collect(toList());
    }
}
