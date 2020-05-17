package ru.itis.shop.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope(value = "custom",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class CustomScopeData {

    private final Set<String> usersName = new HashSet<>();

    public void newUsers(String email){
        usersName.add(email);
        System.out.println("New message from " + email + " in support chat");
    }

    public void deleteSet(){
        usersName.clear();
    }

    public Set<String> showSet(){
        return usersName;
    }

}
