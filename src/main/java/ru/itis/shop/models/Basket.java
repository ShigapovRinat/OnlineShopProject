package ru.itis.shop.models;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Basket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity_good")
    private Integer quantityGood;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;
}
