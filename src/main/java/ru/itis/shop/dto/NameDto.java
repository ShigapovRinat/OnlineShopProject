package ru.itis.shop.dto;

import lombok.Data;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.constraints.Size;

@Data
@RequestScope
public class NameDto {
    @Size(min = 2, message = "{errors.null.name}")
    private String name;

    private String email;

}
