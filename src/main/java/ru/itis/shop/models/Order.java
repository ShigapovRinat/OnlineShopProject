package ru.itis.shop.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"order\"")
public class Order implements Serializable {

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
    private User user;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;
}
