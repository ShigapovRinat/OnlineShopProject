package ru.itis.onlineShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    private GoodDto goodDto;
    private Integer quantityGood;
    private String email;
}