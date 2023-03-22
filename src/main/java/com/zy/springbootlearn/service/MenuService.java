package com.zy.springbootlearn.service;

import com.zy.springbootlearn.common.Result;
import com.zy.springbootlearn.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 呀哈哈
* @description 针对表【t_menu】的数据库操作Service
* @createDate 2023-02-28 17:42:08
*/
public interface MenuService extends IService<Menu> {

    List<Menu> findMenus(String menuName);
}
