package com.ivoriandev.saveursolidaire.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class CloudinaryService {

    @Value("${upload.path}")
    public String uploadDirectory;

    private final Dotenv dotenv = Dotenv.load();

    public String getCloudinaryUrl() {
        return String.format("cloudinary://%s:%s@%s", dotenv.get("CLOUDINARY_API_KEY"),
                dotenv.get("CLOUDINARY_API_SECRET"), dotenv.get("CLOUDINARY_CLOUD_NAME"));
    }

    public String uploadFile(MultipartFile file) throws IOException {
        File convertedFile = convertMultiPartToFile(file);
        Map uploadResult = cloudinary().uploader().upload(convertedFile, getUploadParams());

        boolean isDeleted = convertedFile.delete();
        if (!isDeleted) {
            log.error("Error while deleting file {}", convertedFile.getName());
        }

        return uploadResult.get("secure_url").toString();
    }

    public Map<?,?> getUploadParams() {
        return ObjectUtils.asMap(
                "folder", "saveursolidaire"
        );
    }

    private Cloudinary cloudinary() {
        return new Cloudinary(getCloudinaryUrl());
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());

        String filePath = getDirectory() + originalFilename;

        File convertedFile = new File(filePath);

        if (!convertedFile.getParentFile().exists()) {
            boolean directoryIsCreated = convertedFile.getParentFile().mkdirs();
            if (!directoryIsCreated) {
                throw new IOException("Error while creating directory for file " + convertedFile.getName());
            }
        }

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }

        return convertedFile;
    }

    private String getDirectory() {
        File directory = new File(uploadDirectory);
        if (uploadDirectory.isBlank() || uploadDirectory.isEmpty()) {
            log.error("Upload directory is empty");
            throw new BadRequestException("Upload directory is empty");
        }
        if (!directory.exists()) {
            Boolean makeDirsResult = directory.mkdirs();
            if (makeDirsResult.equals(Boolean.FALSE)) {
                log.error("Error while creating directory {}", uploadDirectory);
                throw new BadRequestException("Error while creating directory " + uploadDirectory);
            }
        }
        return uploadDirectory.endsWith("/") ? uploadDirectory : uploadDirectory + "/";
    }
}