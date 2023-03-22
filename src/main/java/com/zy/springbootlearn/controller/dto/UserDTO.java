package com.zy.springbootlearn.controller.dto;

import com.zy.springbootlearn.entity.Menu;
import lombok.Data;

import java.util.List;

//接收前端登录请求参数

@Data
public class UserDTO {
    private Integer uId;
    private String uName;
    private String uPassword;
    private String uNickname;
    private String avatar;
    private String token;
    private String uRole;
    private List<Menu> menus;
}
