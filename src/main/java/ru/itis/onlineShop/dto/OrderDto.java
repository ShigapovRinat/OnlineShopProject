package ru.itis.onlineShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private List<BasketDto> goodsBasket;
    private LocalDateTime createAt;
    private String email;
    private String idOrder;
}
