package com.example.restapi.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private LocalDateTime joinDate;
}
