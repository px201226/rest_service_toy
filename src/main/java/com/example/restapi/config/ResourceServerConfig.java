package com.example.restapi.config;

import com.example.restapi.config.security.CustomAuthenticationEntryPoint;
import com.example.restapi.config.security.JwtAuthenticationFilter;
import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableResourceServer
@Configuration
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .httpBasic().disable()          // REST API 이므로 기본설정 안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트된다
                .csrf().disable()               // Rest API 이므로 crsf 보안이 필요없다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)         //jwt token으로 인증하므로 세션을 생성안함.
                .and()
                .authorizeRequests()    // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/*/join", "/*/login", "/h2-console/**","/exception/**").permitAll()      //가입 및 인증 주소는 누구나 가능
                .antMatchers(HttpMethod.GET, "/*/matching/nextDay").permitAll()
                .antMatchers(HttpMethod.GET,"/**").permitAll()
                .antMatchers(HttpMethod.GET, "/*/matching/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),       // UsernamePasswordAuth..filter 전에
                        UsernamePasswordAuthenticationFilter.class);                   // JwtAuthenticationFilter 추가
    }
}
