package com.ivoriandev.saveursolidaire.utils.dto.store;

import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import com.ivoriandev.saveursolidaire.utils.dto.file.FileDto;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Store}
 */
@Getter
@Setter
@ToString
@Builder
public class StoreDto implements Serializable {
    Integer id;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    String name;
    String contact;
    String description;
    FileDto file;
    Boolean isActive;
    Location location;
}