package com.zy.springbootlearn.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.springbootlearn.common.Result;
import com.zy.springbootlearn.entity.RoleMenu;
import com.zy.springbootlearn.service.RoleMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单关系表(RoleMenu)表控制层
 *
 * @author makejava
 * @since 2023-03-22 11:14:53
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
    /**
     * 服务对象
     */
    @Resource
    private RoleMenuService roleMenuService;

    /**
     * 绑定角色和菜单的关系
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/{roleId}")
    public Result setRoleMenu(@PathVariable Integer roleId , @RequestBody List<Integer> menuIds) {
        roleMenuService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return Result.success(roleMenuService.getRoleMenu(roleId));
    }
    /**
     * 分页查询所有数据
     *
     * @param pageNo 第几页
     * @param pageSize 每页的大小
     * @return 所有数据
     */
/*    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNo,@RequestParam Integer pageSize) {
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        //queryWrapper.orderByDesc("");
        return Result.success(this.roleMenuService.page(new Page<>(pageNo,pageSize),wrapper));
    }*/

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
/*    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.roleMenuService.getById(id));
    }*/
/*
    /**
     * 新增数据
     *
     * @param roleMenu 实体对象
     * @return 新增结果
     */
/*    @PostMapping
    public Result insert(@RequestBody RoleMenu roleMenu) {
        return Result.success(this.roleMenuService.save(roleMenu));
    }*/

    /**
     * 修改数据
     *
     * @param roleMenu 实体对象
     * @return 修改结果
     */
/*    @PutMapping
    public Result update(@RequestBody RoleMenu roleMenu) {
        return Result.success(this.roleMenuService.updateById(roleMenu));
    }*/
        /**
     * 添加或修改数据
     * @param role
     * @return
     */
/*    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody RoleMenu roleMenu){
        this.roleService.saveOrUpdate(roleMenu);
        return Result.success();
    }*/
    
    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
/*    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.success(this.roleMenuService.removeByIds(idList));
    }*/
}

