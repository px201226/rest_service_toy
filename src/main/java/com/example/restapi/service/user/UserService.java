package com.example.restapi.service.user;

import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //loadUserByUserEmail
    @Override
    public User loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userRepository.findByEmail(userPk)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("사용자(%s)를 찾을 수 없습니다", userPk))
                );
    }

    public User join(User user){
        return userRepository.save(User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }
}
