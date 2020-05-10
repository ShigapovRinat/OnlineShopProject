package ru.itis.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SessionScope
public class BasketDto {
    private GoodDto goodDto;
    private Integer quantityGood;
    private String email;
    private Long id;
}
