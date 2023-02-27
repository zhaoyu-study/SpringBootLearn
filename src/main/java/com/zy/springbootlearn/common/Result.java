package com.zy.springbootlearn.common;
//统一包装类，和前端约定什么代表什么，统一返回的数据
/**
 * 接口统一的返回包装类
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code;
    private String msg;//返回原因
    private Object data;//返回的数据

    /**
     * 请求成功
     * @return
     */
    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }
    /**
     * 请求成功,返回数据
     * @return
     */
    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }
    /**
     * 请求失败
     * @return
     */
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }

    /**
     * 普通的错误，系统错误
     * @return
     */
    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }
}
