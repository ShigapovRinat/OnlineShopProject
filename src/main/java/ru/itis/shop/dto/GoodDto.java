package ru.itis.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.shop.models.Good;
import ru.itis.shop.models.GoodType;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodDto {

    private Long id;
    private String title;
    private String manufacturer;
    private String description;
    private Integer price;
    private GoodType type;
    private String path;
    private MultipartFile multipartFile;

    public static GoodDto from(Good good){
        return GoodDto.builder()
                .id(good.getId())
                .description(good.getDescription())
                .title(good.getTitle())
                .price(good.getPrice())
                .type(good.getType())
                .manufacturer(good.getManufacturer())
                .path(good.getPath())
                .build();
    }

    public static List<GoodDto> from(List<Good> goods){
        return goods.stream()
                .map(GoodDto::from)
                .collect(Collectors.toList());
    }
}
