package com.ivoriandev.saveursolidaire.utils.dto.file;

import com.ivoriandev.saveursolidaire.models.File;
import com.ivoriandev.saveursolidaire.utils.enums.file.FileTypeEnum;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link File}
 */
@Value
public class FileDto implements Serializable {
    Integer id;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    String name;
    FileTypeEnum type;
    String url;
}