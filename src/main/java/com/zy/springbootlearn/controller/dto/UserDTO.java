package com.zy.springbootlearn.controller.dto;

import lombok.Data;

//接收前端登录请求参数

@Data
public class UserDTO {
    private Integer uId;
    private String uName;
    private String uPassword;
    private String uNickname;
    private String avatar;
    private String token;
}
