package com.ivoriandev.saveursolidaire.utils.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenDto {
    @NotNull
    @NotEmpty
    private String token;

    public static TokenDto from(AuthenticationResponse response) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(response.getToken());

        return tokenDto;
    }
}
