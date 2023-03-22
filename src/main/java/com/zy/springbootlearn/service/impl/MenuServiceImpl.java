package com.zy.springbootlearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.springbootlearn.common.Result;
import com.zy.springbootlearn.entity.Menu;
import com.zy.springbootlearn.service.MenuService;
import com.zy.springbootlearn.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 呀哈哈
* @description 针对表【t_menu】的数据库操作Service实现
* @createDate 2023-02-28 17:42:08
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    /**
     * 封装一个获取菜单数据关系的方法
     * @param menuName
     * @return
     */
    @Override
    public List<Menu> findMenus(String menuName) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if(!menuName.equals(""))
            wrapper.like("menu_name",menuName);
        //查询所有数据
        List<Menu> list = list(wrapper);
        //先找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : list) {
            menu.setChildren(list.stream().filter(m -> menu.getMenuId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}




