package ru.itis.onlineShop.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_support")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Column(name = "whom_id")
    private Long whomId;

    @Column(name = "from_id")
    private Long fromId;

    @Column(name = "create_at")
    private LocalDateTime createAt;

}
