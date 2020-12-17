package com.example.restapi.config.security;

import com.example.restapi.exception.UserNotFoundException;
import com.example.restapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userRepository.findById(Integer.valueOf(userPk))
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("사용자(%d)를 찾을 수 없습니다", Integer.valueOf(userPk)))
                );
    }
}
