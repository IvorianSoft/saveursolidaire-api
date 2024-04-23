package com.ivoriandev.saveursolidaire.utils.dto.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import com.ivoriandev.saveursolidaire.utils.dto.file.FileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Store}
 */
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public static StoreDto from(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .deletedAt(store.getDeletedAt())
                .name(store.getName())
                .contact(store.getContact())
                .description(store.getDescription())
                .file(FileDto.from(store.getLogo()))
                .isActive(store.getIsActive())
                .location(store.getLocation())
                .build();
    }
}
