package com.zy.springbootlearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.springbootlearn.common.Constants;
import com.zy.springbootlearn.controller.dto.UserDTO;
import com.zy.springbootlearn.entity.Menu;
import com.zy.springbootlearn.entity.User;
import com.zy.springbootlearn.exception.ServiceException;
import com.zy.springbootlearn.mapper.RoleMapper;
import com.zy.springbootlearn.mapper.RoleMenuMapper;
import com.zy.springbootlearn.mapper.UserMapper;
import com.zy.springbootlearn.service.IUserService;
import com.zy.springbootlearn.service.MenuService;
import com.zy.springbootlearn.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuService menuService;
    /**
     * 将此类的重复代码封装
     * @param userDTO
     * @return
     */
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

    @Override
    public UserDTO login(UserDTO userDTO) {

        User one = getUserInfo(userDTO);

        if(one != null){//能查出结果
            BeanUtils.copyProperties(one,userDTO);//用one里的赋值userDTO相同属性的值
            //设置token
            String token = TokenUtils.generateToken(one.getUId().toString(), one.getUPassword());
            userDTO.setToken(token);

            String roleKey = one.getURole();//role --> ROLE_ADMIN

            //调用封装的函数，获取当前user的对应menu
            List<Menu> roleMenus = getRoleMenus(roleKey);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }

    }

    @Override
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


    /**
     * 获取某登录user对应的menu权限菜单列表
     * @param roleKey
     * @return
     */
    private List<Menu> getRoleMenus(String roleKey){
        //查询t_role表中的与roleKey（ROLE_ADMIN）数据对应的roleId
        Integer roleId = roleMapper.selectByRoleKey(roleKey);

        //查询t_role_menu表中的roleId对应的menuId
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        //查出系统所有的菜单
        List<Menu> menus = menuService.findMenus("");
        //new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        //筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if(menuIds.contains(menu.getMenuId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();

            //解决因为没有分配父节点只有子节点数据而被上面忽略的节点数据
            if ((!children.isEmpty())&&roleMenus.isEmpty()){
                roleMenus.add(menu);
            }

            //removeIf  移除children 里面不在 menuIds 集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getMenuId()));
        }
        return roleMenus;
    }

}
