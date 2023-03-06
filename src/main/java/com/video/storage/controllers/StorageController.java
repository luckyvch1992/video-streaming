package com.video.storage.controllers;

import com.video.storage.services.interfaces.StorageService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("upload")

public class StorageController {

    private StorageService storageService;

    public Mono<Void> upload(@RequestPart("file") Mono<FilePart> filePartMono) {
//        MultipartFile multipartFile;
//        multipartFile.
        return filePartMono
//                .flatMap(filePart -> filePart.content().)
                .then();
    }
}
