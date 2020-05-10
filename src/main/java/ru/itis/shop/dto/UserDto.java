package ru.itis.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.annotation.SessionScope;
import ru.itis.shop.models.User;
import ru.itis.shop.models.UserRole;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SessionScope
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private boolean isConfirmed;
    private UserRole role;

    public static UserDto from(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .isConfirmed(user.isConfirmed())
                .role(user.getRole())
                .build();
    }

    public static List<UserDto> from(List<User> people){
        return people.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
