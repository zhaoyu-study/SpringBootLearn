package com.zy.springbootlearn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.springbootlearn.entity.Menu;
import com.zy.springbootlearn.service.MenuService;
import com.zy.springbootlearn.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author 呀哈哈
* @description 针对表【t_menu】的数据库操作Service实现
* @createDate 2023-02-28 17:42:08
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




