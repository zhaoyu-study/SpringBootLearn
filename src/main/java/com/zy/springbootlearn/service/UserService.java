/*package com.zy.springbootlearn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.springbootlearn.common.Constants;
import com.zy.springbootlearn.controller.dto.UserDTO;
import com.zy.springbootlearn.entity.User;
import com.zy.springbootlearn.exception.ServiceException;
import com.zy.springbootlearn.mapper.UserMapper;
import com.zy.springbootlearn.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    *//**
     * 将此类的重复代码封装
     * @param userDTO
     * @return
     *//*
    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("u_name",userDTO.getUName());
        queryWrapper.eq("u_password", userDTO.getUPassword());

        User one;
        try {
            one = getOne(queryWrapper);//如果结果不唯一，则抛出异常
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "系统错误，结果不唯一");
        }
        return one;
    }


    public UserDTO login(UserDTO userDTO) {

        User one = getUserInfo(userDTO);

        if(one != null){//能查出结果
            BeanUtils.copyProperties(one,userDTO);//用one里的赋值userDTO相同属性的值
            //设置token
            String token = TokenUtils.generateToken(one.getUId().toString(), one.getUPassword());
            userDTO.setToken(token);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }

    }

    public void register(UserDTO userDTO) {

        User one = getUserInfo(userDTO);

        if(one == null){//能查出结果，说明用户名与密码重复了
            one = new User();           //这里不new的话下面报空指针异常
            BeanUtils.copyProperties(userDTO,one);
            save(one);
        }else {
            throw new ServiceException(Constants.CODE_700, "用户名与密码重复，请修改用户名或密码");
        }
    }


}*/
/*        try {
            User one = getOne(queryWrapper);//如果结果不唯一，则抛出异常
            if(one != null){//能查出结果
                BeanUtils.copyProperties(one,userDTO);//用one里的赋值userDTO相同属性的值
                return userDTO;
            }else {
                throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
            }
        }catch (Exception e){
*//*            LOG.error(e);*//*
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }*/
//以上600异常会被catch到，被覆盖成500异常，错误
