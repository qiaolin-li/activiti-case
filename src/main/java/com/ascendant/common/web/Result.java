package com.ascendant.common.web;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *  请求响应类
 * @author qiaolin
 * @version 2018/10/24
 **/


@Getter
@Setter
public class Result {

    private boolean success = true;
    private String msg = "操作成功！";
    private Map<String, Object> data = new HashMap<>();

    /**
     *  添加其他数据
     * @param key
     * @param value
     */
    public void put(String key, String value){
        data.put(key, value);
    }

    /**
     *  删除已经添加得数据
     * @param key
     */
    public void remove(String key){
        data.remove(key);
    }

    /**
     *  返回一个成功的响应对象
     * @return
     */
    public static Result ok(){
        return new Result();
    }

    /**
     *  返回一个成功的响应对象并指定消息
     * @param msg 消息
     * @return
     */
    public static Result ok(String msg){
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }

    /**
     *  返回一个失败的响应消息，消息默认为操作失败
     * @return
     */
    public static Result error(){
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg("操作失败!");
        return result;
    }

    /**
     *  返回一个失败的响应消息，并指定消息
     * @return
     */
    public static Result error(String msg){
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }


}
