package com.example.restapi.config.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
* Spring Security 적용 후, 인증 되지 않은 사용자가 요청할 경우 dispatchselvet까지 오지못하고
* 토근 검증단에서 끝나기 때문에 예외핸들러에 잡히지 않으므로 다음 클래스를 등록한다. */

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException,
            ServletException {
        response.sendRedirect("/exception/entrypoint");
    }
}