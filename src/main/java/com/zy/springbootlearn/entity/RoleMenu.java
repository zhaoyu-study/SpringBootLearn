package com.zy.springbootlearn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色菜单关系表
 * @TableName t_role_menu
 */
@TableName(value ="t_role_menu")
@Data
public class RoleMenu implements Serializable {
    /**
     * 角色ID
     */
    @TableId
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;

}