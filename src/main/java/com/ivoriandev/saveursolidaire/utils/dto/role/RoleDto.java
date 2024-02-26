package com.ivoriandev.saveursolidaire.utils.dto.role;

import com.ivoriandev.saveursolidaire.models.Role;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Role}
 */
@Getter
@Setter
@ToString
@Builder
public class RoleDto implements Serializable {
    Integer id;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    String name;

    public static RoleDto from(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .deletedAt(role.getDeletedAt())
                .name(role.getName())
                .build();
    }
}