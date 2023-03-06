package com.video.storage.services.interfaces;

import com.video.storage.entity.MediaFile;

import java.util.List;

public interface StorageService {

    void upload(MediaFile mediaFile);

    List<MediaFile> findAll();
}
