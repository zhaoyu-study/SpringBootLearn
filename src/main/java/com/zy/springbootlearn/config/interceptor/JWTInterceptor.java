package com.zy.springbootlearn.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zy.springbootlearn.common.Constants;
import com.zy.springbootlearn.entity.User;
import com.zy.springbootlearn.exception.ServiceException;
import com.zy.springbootlearn.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");

        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        //获取token里的userId
        String ID;
        try {
            ID = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401, "token验证失败");
        }
        //根据token中的userID查询数据库
        User user = userService.getById(Integer.parseInt(ID));
        if (user == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }
        //用户密码加上签名 验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        return true;
    }
}
