package com.ivoriandev.saveursolidaire.utils.dto.user;

import com.ivoriandev.saveursolidaire.models.User;
import com.ivoriandev.saveursolidaire.utils.dto.role.RoleDto;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link User}
 */
@Getter
@Setter
@ToString
@Builder
public class UserDto implements Serializable {
    Integer id;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    String name;
    String email;
    String contact;
    String password;
    Boolean isActive;
    RoleDto role;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .name(user.getName())
                .email(user.getEmail())
                .contact(user.getContact())
                .password(user.getPassword())
                .isActive(user.getIsActive())
                .role(RoleDto.from(user.getRole()))
                .build();
    }
}