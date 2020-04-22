package ru.itis.shop.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer price;

    @Enumerated(value = EnumType.STRING)
    private GoodType type;

    @OneToMany(mappedBy = "good")
    private Set<Basket> basket;

    @OneToMany(mappedBy = "good")
    private Set<Order> orders;

}
