package com.zy.springbootlearn.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @TableName t_role
 */
@TableName(value ="t_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    /**
     * 名称
     */
    private String roleName;

    /**
     * 描述
     */
    private String roleDescription;

    /**
     * 角色 唯一标识
     */
    private String roleKey;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Boolean deleted;
    /*@TableField(exist = false)
    private static final long serialVersionUID = 1L;*/
}