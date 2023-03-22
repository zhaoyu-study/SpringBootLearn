package com.zy.springbootlearn.service;

import com.zy.springbootlearn.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 呀哈哈
* @description 针对表【t_role_menu(角色菜单关系表)】的数据库操作Service
* @createDate 2023-03-22 11:14:25
*/
public interface RoleMenuService extends IService<RoleMenu> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
