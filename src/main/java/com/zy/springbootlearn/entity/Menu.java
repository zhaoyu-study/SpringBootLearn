package com.zy.springbootlearn.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_menu
 */
@TableName(value ="t_menu")
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    /**
     * 
     */
    private String menuName;

    /**
     * 菜单路径
     */
    private String menuPath;

    /**
     * 图标
     */
    private String menuIcon;

    /**
     * 描述
     */
    private String menuDescription;

    /**
     * 
     */

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Boolean deleted;

/*    @TableField(exist = false)
    private static final long serialVersionUID = 1L;*/
}