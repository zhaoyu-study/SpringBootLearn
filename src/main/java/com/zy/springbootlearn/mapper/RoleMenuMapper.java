package com.zy.springbootlearn.mapper;

import com.zy.springbootlearn.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 呀哈哈
* @description 针对表【t_role_menu(角色菜单关系表)】的数据库操作Mapper
* @createDate 2023-03-22 11:14:25
* @Entity com.zy.springbootlearn.entity.RoleMenu
*/
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    @Select("select menu_id from t_role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);

}




