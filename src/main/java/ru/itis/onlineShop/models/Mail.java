package ru.itis.onlineShop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> model;
}
