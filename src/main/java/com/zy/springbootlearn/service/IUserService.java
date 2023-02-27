package com.zy.springbootlearn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.springbootlearn.controller.dto.UserDTO;
import com.zy.springbootlearn.entity.User;

public interface IUserService extends IService<User> {
    UserDTO login(UserDTO userDTO);

    void register(UserDTO userDTO);
}
