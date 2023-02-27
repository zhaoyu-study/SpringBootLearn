package com.zy.springbootlearn.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.zy.springbootlearn.common.Result;
import com.zy.springbootlearn.entity.User;
import com.zy.springbootlearn.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private IUserService userService;
    /**
     * 测试：展示一些假数据
     * @return
     */
    @GetMapping("/line")
    public Result get(){
        Map<String, Object> map = new HashMap<>();
        map.put("x",CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y",CollUtil.newArrayList(820, 932, 901, 934, 1290, 1330, 1320));
        return Result.success(map);
    }

    /**
     * 展示每个季度的会员人数  member 成员
     * @return
     */
    @GetMapping("/members")
    public Result members(){
        List<User> list = userService.list();
        //定义季度
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        //键入 iter 快速生成 for 循环框架
        for (User user : list) {
            Date createTime = user.getCreateTime();
            //根据时间自动获得其 季度数
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1:q1 +=1;break;
                case Q2:q2 +=1;break;
                case Q3:q3 +=1;break;
                case Q4:q4 +=1;break;
                default:break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

}
