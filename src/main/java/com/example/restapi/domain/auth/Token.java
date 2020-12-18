package com.example.restapi.domain.auth;


import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String jwtToken;
}
