package com.example.restapi.domain.user;

import com.example.restapi.domain.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;

public class UserAdapter extends org.springframework.security.core.userdetails.User {
    private User user;

    public UserAdapter(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
