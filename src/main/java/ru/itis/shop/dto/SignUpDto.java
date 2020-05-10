package ru.itis.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @Size(min = 2, message = "{errors.null.name}")
    private String name;

    @Size(min = 1)
    @Email(message = "{errors.incorrect.email}")
    private String email;

    @Size(min = 3, message = "{errors.small.password}")
    @Pattern(regexp = "((?=.*\\d)(?=.*[A-Za-z]).{3,15})", message = "{errors.incorrect.password}")
    private String password;
}
