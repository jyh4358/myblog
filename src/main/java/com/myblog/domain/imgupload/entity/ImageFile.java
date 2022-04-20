package com.myblog.domain.imgupload.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class ImageFile {

    private String originalFileName;
    private String storedFileName;
    MultipartFile multipartFile;


    @Builder
    public ImageFile(String originalFileName, String storedFileName, MultipartFile multipartFile) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.multipartFile = multipartFile;
    }


    public static ImageFile from(MultipartFile multipartFile) {
        return ImageFile.builder()
                .originalFileName(multipartFile.getOriginalFilename())
                .storedFileName(createStoreFileName(multipartFile.getOriginalFilename()))
                .multipartFile(multipartFile)
                .build();
    }

    private static String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private static String extractExt(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

    }
}
