package com.ivoriandev.saveursolidaire.utils.dto.auth;

import com.ivoriandev.saveursolidaire.utils.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AuthenticationResponse {
    private String token;
}
