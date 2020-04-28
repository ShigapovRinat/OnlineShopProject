package ru.itis.shop.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class User implements Serializable {

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
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Basket> basket;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private Set<Order> orders;

    @OneToMany
    @JoinColumn(name = "from_id")
    private Set<Message> messages;
}
