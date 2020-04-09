package ru.itis.onlineShop.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "quantity_good")
    private Integer quantityGood;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;
}
