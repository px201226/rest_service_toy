package com.example.restapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "my-app")
public class AppProperties {

    @NotEmpty
    private String ClientId;

    @NotEmpty
    private String ClientSecret;

}
