package ru.itis.onlineShop.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String hashPassword;
    private String confirmLink;
    private boolean isConfirmed;

    @Enumerated(value = EnumType.STRING)
    private PersonRole role;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private Set<Basket> basket;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
    private Set<Message> messages;
}
