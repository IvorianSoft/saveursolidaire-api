package com.ivoriandev.saveursolidaire.utils.dto.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivoriandev.saveursolidaire.models.File;
import com.ivoriandev.saveursolidaire.utils.enums.file.FileTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link File}
 */
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDto implements Serializable {
    Integer id;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    String name;
    FileTypeEnum type;
    String url;

    public static FileDto from(File file) {
        if (file == null) {
            return null;
        }
        return FileDto.builder()
                .id(file.getId())
                .name(file.getName())
                .type(file.getType())
                .url(file.getUrl())
                .build();
    }
}
