package com.zy.springbootlearn.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer uId;


    @TableField(exist = false)
    private Integer sId;//序列号

    @Alias("用户名")
    private String uName;

    //@JsonIgnore //如果不想展示密码，如果需要增加或修改，需要表单提交，json提交会忽略
    private String uPassword;

    @Alias("昵称")
    private String uNickname;

    @Alias("邮箱")
    private String uEmail;

    @Alias("电话")
    private String uPhone;

    @Alias("地址")
    private String uAddress;

    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Boolean deleted;
}
