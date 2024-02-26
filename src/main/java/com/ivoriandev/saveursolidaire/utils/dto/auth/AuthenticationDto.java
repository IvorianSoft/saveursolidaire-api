package com.ivoriandev.saveursolidaire.utils.dto.auth;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class AuthenticationDto {
    @NonNull
    private String email;
    @NonNull
    @Size(min = 8)
    private String password;
}
