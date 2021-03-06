package ru.itis.shop.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Good implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String manufacturer;
    private String description;
    private Integer price;
    private String path;

    @Enumerated(value = EnumType.STRING)
    private GoodType type;

    @OneToMany(mappedBy = "good")
    private Set<Basket> basket;

    @OneToMany(mappedBy = "good")
    private Set<Order> orders;

}
