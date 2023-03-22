package com.zy.springbootlearn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_dict
 */
@TableName(value ="t_dict")
@Data
public class Dict implements Serializable {
    /**
     * 名称
     */
    private String dictName;

    /**
     * 内容
     */
    private String dictValue;

    /**
     * 类型
     */
    private String dictType;

}