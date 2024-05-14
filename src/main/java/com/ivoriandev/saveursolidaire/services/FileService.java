package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.models.File;
import com.ivoriandev.saveursolidaire.repositories.FileRepository;
import com.ivoriandev.saveursolidaire.utils.enums.file.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public File saveImage(MultipartFile file) {
        File image = formatFile(file, FileTypeEnum.IMAGE);
        String url;

        try {
            url = cloudinaryService.uploadFile(file);
            image.setUrl(url);

            return fileRepository.save(image);
        } catch (Exception e) {
            log.error("Error while uploading file {}", file.getOriginalFilename());
            throw new RuntimeException("Error while uploading file");
        }
    }

    private File formatFile(MultipartFile file, FileTypeEnum fileType) {
        return File.builder()
                .name(file.getOriginalFilename())
                .type(fileType)
                .build();
    }
}
