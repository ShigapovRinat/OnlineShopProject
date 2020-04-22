package ru.itis.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.shop.models.Message;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private String pageId;
    private String text;
    private Long fromId;
    private Long whomId;

    public static MessageDto from(Message message){
        return MessageDto.builder()
                .fromId(message.getFromId())
                .text(message.getText())
                .whomId(message.getWhomId())
                .build();
    }

    public static List<MessageDto> from(List<Message> messages){
        return messages.stream()
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }
}