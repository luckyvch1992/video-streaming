package com.video.storage.repository;

import com.video.storage.entity.MediaFile;
import org.springframework.data.repository.CrudRepository;

public interface MediaStorageRepository extends CrudRepository<MediaFile, Long> {

}
