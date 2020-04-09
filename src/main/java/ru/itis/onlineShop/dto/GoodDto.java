package ru.itis.onlineShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.GoodType;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodDto {

    private Long id;
    private String title;
    private String description;
    private Integer price;
    private GoodType type;

    public static GoodDto from(Good good){
        return GoodDto.builder()
                .id(good.getId())
                .description(good.getDescription())
                .title(good.getTitle())
                .price(good.getPrice())
                .type(good.getType())
                .build();
    }

    public static List<GoodDto> from(List<Good> goods){
        return goods.stream()
                .map(GoodDto::from)
                .collect(Collectors.toList());
    }
}
