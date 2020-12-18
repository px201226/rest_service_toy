package com.example.restapi.user;

import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserInfo2")
public class UserV2 extends User {
    private String grade;
}
