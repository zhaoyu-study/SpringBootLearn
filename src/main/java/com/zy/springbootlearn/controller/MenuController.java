package com.zy.springbootlearn.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.springbootlearn.common.Result;
import com.zy.springbootlearn.entity.Menu;
import com.zy.springbootlearn.entity.Role;
import com.zy.springbootlearn.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Menu)表控制层
 *
 * @author makejava
 * @since 2023-02-28 17:46:13
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Resource
    private MenuService menuService;

    /**
     * 分页查询所有数据
     *
     * @param pageNo 第几页
     * @param pageSize 每页的大小
     * @return 所有数据
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize ,@RequestParam(defaultValue = "") String menuName ) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if(!menuName.equals(""))
            wrapper.like("menu_name",menuName);//这里使用数据库字段名，不要用User属性名
        //queryWrapper.orderByDesc("");
        return Result.success(this.menuService.page(new Page<>(pageNo,pageSize),wrapper));
    }

    @GetMapping("/findAll")
    public Result findAll(@RequestParam(defaultValue = "") String menuName){
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if(!menuName.equals(""))
            wrapper.like("menu_name",menuName);
        //查询所有数据
        List<Menu> list = menuService.list(wrapper);
        //先找出pid为null的一级菜单
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : list) {
            menu.setChildren(list.stream().filter(m -> menu.getMenuId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return Result.success(parentNode);
    }


    /**
     * 添加或修改数据
     * @param menu
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody Menu menu){
        this.menuService.saveOrUpdate(menu);
        return Result.success();
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectById/{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.menuService.getById(id));
    }



    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/delBatchById")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(this.menuService.removeByIds(ids));
    }
}

