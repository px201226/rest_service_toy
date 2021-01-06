package com.example.restapi.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // Basic Auth Login
    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("dlrlem")
                .password("{noop}test1234")
                .roles("USER");
    }*/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .httpBasic().disable()          // REST API 이므로 기본설정 안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트된다
                .csrf().disable()               // Rest API 이므로 crsf 보안이 필요없다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)         //jwt token으로 인증하므로 세션을 생성안함.
                .and()
                    .authorizeRequests()    // 다음 리퀘스트에 대한 사용권한 체크
                    .antMatchers("/*/join", "/*/login", "/h2-console/**","/exception/**").permitAll()       //가입 및 인증 주소는 누구나 가능
                    .antMatchers(HttpMethod.GET,"/**").permitAll()
                    .anyRequest().hasRole("USER")
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                  .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),       // UsernamePasswordAuth..filter 전에
                         UsernamePasswordAuthenticationFilter.class);                   // JwtAuthenticationFilter 추가
    }


    // ignore check swagger resource
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/h2-console/**");

    }
}
