package ru.itis.shop.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.UserDto;
import ru.itis.shop.models.UserRole;
import ru.itis.shop.services.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsersService usersService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
        try {
            UserDto userDto = usersService.findPerson(user.getEmail());
        } catch (IllegalArgumentException e){
            usersService.addUser(UserDto.builder()
                    .email(user.getEmail())
                    .isConfirmed(true)
                    .name(user.getFullName())
                    .role(UserRole.USER)
                    .build()
            );
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
