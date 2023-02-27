package com.zy.springbootlearn.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zy.springbootlearn.entity.User;
import com.zy.springbootlearn.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService = userService;
    }
    /**
     *生成token,
     * ID是实体类id,uId
     * signKey就是当前用户的密码,uPassword
     * @param ID
     * @param signKey
     * @return
     */
    public static  String generateToken(String ID,String signKey){
         return JWT.create().withAudience(ID) //将uName保存到token
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//设置token过期时间,2h 后
                .sign(Algorithm.HMAC256(signKey));//以password作为token的密钥
    }

    /**
     * 获取当前登录的用户信息
     * 静态方法不需要new，直接引用就行
     * 引用：TokenUtils.getCurrentUser();
     * 有可能是null，需要作判定
     * @return
     */
    //静态方法不能引用非静态属性
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String uId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(uId);
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
