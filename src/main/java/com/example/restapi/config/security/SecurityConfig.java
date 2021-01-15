package com.example.restapi.config.security;

import com.example.restapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.swing.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // Basic Auth Login
    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("dlrlem")
                .password("{noop}test1234")
                .roles("USER");
    }*/

    // TokenStore
    // OAuth 토큰을 저장 InMemoryTokenStore 사용
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    /* 다른 AuthorizationServer나 ResourceServer가 참조할 수 있도록 오버라이딩 해서 빈으로 등록
    * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    // AuthenticationManager 재정의
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

//     Spring security 설정, oauth 서버가 대신한다.
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
                    .antMatchers(HttpMethod.GET, "/*/matching/nextDay").permitAll()
                    .antMatchers(HttpMethod.GET, "/*/matching/**").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/**").permitAll()
                    .anyRequest().hasRole("USER")
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                  .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),       // UsernamePasswordAuth..filter 전에
                         UsernamePasswordAuthenticationFilter.class);                   // JwtAuthenticationFilter 추가
    }
//

    // ignore check swagger resource
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/h2-console/**");

        //스프링 부트가 제공하는 static 리소스들의 기본위치를 다 가져와서 스프링 시큐리티에서 제외
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
