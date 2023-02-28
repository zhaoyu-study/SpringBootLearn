package com.zy.springbootlearn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.springbootlearn.common.Result;
import com.zy.springbootlearn.entity.Role;
import com.zy.springbootlearn.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Role)表控制层
 *
 * @author makejava
 * @since 2023-02-27 23:26:13
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 分页查询所有数据
     *
     * @param pageNo 第几页
     * @param pageSize 每页的大小
     * @return 所有数据
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam(defaultValue = "") String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!roleName.equals(""))
            wrapper.like("role_name",roleName);//这里使用数据库字段名，不要用User属性名
        //wrapper.orderByAsc("role_id");
        return Result.success(this.roleService.page(new Page<Role>(pageNo,pageSize),wrapper));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param roleId 主键
     * @return 单条数据
     */
    @GetMapping("/selectById/{roleId}")
    public Result selectOne(@PathVariable Serializable roleId) {
        return Result.success(this.roleService.getById(roleId));
    }


    /**
     * 添加或修改数据
     * @param role
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody Role role){
        this.roleService.saveOrUpdate(role);
        return Result.success();
    }
    /**
     * 删除数据
     *
     * @param ids 主键集合
     * @return 删除结果
     */
    @DeleteMapping("/delBatchById")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(this.roleService.removeByIds(ids));
    }
}

