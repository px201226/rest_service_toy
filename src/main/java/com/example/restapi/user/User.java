package com.example.restapi.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
//@JsonIgnoreProperties(value = {"password"})
//@JsonFilter("UserInfo")
public class User {

    private Integer id;

    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;

    @Past
    @ApiModelProperty(notes = "등록일을 입력해 주세요.")
    private LocalDateTime joinDate;

   // @JsonIgnore                                     //Json 응답에 숨김
   @ApiModelProperty(notes = "패스워드를 입력해 주세요.")
    private String password;

   // @JsonIgnore                                     //Json 응답에 숨김
   @ApiModelProperty(notes = "주민번호를 입력해 주세요.")
    private String ssn;

}
