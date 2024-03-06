package com.ivoriandev.saveursolidaire.utils.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@ToString
public class UserRegisterDto {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Length(min = 10, max = 10)
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private String contact;
    @NotNull
    @Length(min = 8, max = 50)
    private String password;
}
