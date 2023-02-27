package com.zy.springbootlearn.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("t_file")
public class Files {

    @TableId(type = IdType.AUTO)
    private Integer fileId;

    @TableField(exist = false)
    private Integer fileSid;//序列号

    private String fileName;

    private String fileType;

    private Long fileSize;

    private String fileMd5;

    private String fileUrl;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Boolean deleted;

    private Boolean enable;
}
