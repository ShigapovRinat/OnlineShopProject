package ru.itis.shop.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PasswordDto {
    @Size(min = 3, message = "{errors.small.password}")
    @Pattern(regexp = "((?=.*\\d)(?=.*[A-Za-z]).{3,15})", message = "{errors.incorrect.password}")
    private String password;

    private String email;
}
