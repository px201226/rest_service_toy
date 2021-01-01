package com.example.restapi.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 jwt Token의 유효성을 감증하는 filter를  filterChain에 등록한다.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter 작동중");
        String token = jwtTokenProvider.resolveToken((HttpServletRequest)servletRequest);
        System.out.println(token);
        if(token != null && jwtTokenProvider.validateToken(token)){
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            System.out.println("성공");
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
