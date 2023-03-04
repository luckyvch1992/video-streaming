package com.video.storage.services.impl;

import com.video.storage.entity.MediaFile;
import com.video.storage.services.interfaces.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public List<MediaFile> findAll() {
        return null;
    }
}
