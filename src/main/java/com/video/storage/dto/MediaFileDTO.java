package com.video.storage.dto;

import lombok.Builder;

@Builder
public record MediaFileDTO(String id, String fileName, String mimeType, Long fileSize) {

}
