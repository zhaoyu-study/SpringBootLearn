package com.zy.springbootlearn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.springbootlearn.entity.Role;
import com.zy.springbootlearn.service.RoleService;
import com.zy.springbootlearn.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 呀哈哈
* @description 针对表【t_role】的数据库操作Service实现
* @createDate 2023-02-27 21:43:55
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

}




