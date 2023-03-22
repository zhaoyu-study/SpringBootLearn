package com.zy.springbootlearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.springbootlearn.entity.RoleMenu;
import com.zy.springbootlearn.service.RoleMenuService;
import com.zy.springbootlearn.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author 呀哈哈
* @description 针对表【t_role_menu(角色菜单关系表)】的数据库操作Service实现
* @createDate 2023-03-22 11:14:25
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional //要么一起执行要么不执行 回滚
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        /*QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);*/

        //先删除当前角色ID所有的绑定关系
        roleMenuMapper.deleteById(roleId);

        //再把前端传来的菜单ID数组绑定到当前角色ID上去
        for (Integer menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {

        return roleMenuMapper.selectByRoleId(roleId);
    }
}




