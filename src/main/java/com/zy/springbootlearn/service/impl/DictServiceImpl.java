package com.zy.springbootlearn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.springbootlearn.entity.Dict;
import com.zy.springbootlearn.service.DictService;
import com.zy.springbootlearn.mapper.DictMapper;
import org.springframework.stereotype.Service;

/**
* @author 呀哈哈
* @description 针对表【t_dict】的数据库操作Service实现
* @createDate 2023-03-21 22:44:53
*/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
    implements DictService{

}




