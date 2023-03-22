package com.zy.springbootlearn.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.springbootlearn.common.Constants;
import com.zy.springbootlearn.common.Result;
import com.zy.springbootlearn.entity.Dict;
import com.zy.springbootlearn.service.DictService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import static com.zy.springbootlearn.common.Constants.DICT_TYPE_ICON;

/**
 * (Dict)表控制层
 *
 * @author makejava
 * @since 2023-03-21 22:47:19
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    /**
     * 服务对象
     */
    @Resource
    private DictService dictService;


    @GetMapping("/icons")
    public Result getIcons() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", Constants.DICT_TYPE_ICON);
        return Result.success(dictService.list(queryWrapper));
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
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        //queryWrapper.orderByDesc("");
        return Result.success(this.dictService.page(new Page<>(pageNo,pageSize),wrapper));
    }*/

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
/*    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.dictService.getById(id));
    }*/
/*
    /**
     * 新增数据
     *
     * @param dict 实体对象
     * @return 新增结果
     */
/*
    @PostMapping
    public Result insert(@RequestBody Dict dict) {
        return Result.success(this.dictService.save(dict));
    }
*/

    /**
     * 修改数据
     *
     * @param dict 实体对象
     * @return 修改结果
     */
/*
    @PutMapping
    public Result update(@RequestBody Dict dict) {
        return Result.success(this.dictService.updateById(dict));
    }
*/

        /**
     * 添加或修改数据
     * @param role
     * @return
     */
/*    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody Dict dict){
        this.roleService.saveOrUpdate(dict);
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
        return Result.success(this.dictService.removeByIds(idList));
    }*/
}

