package com.zlj.Vo;

import lombok.Data;

/**
 * projectName: Kataba
 * author: 张留佳
 * time: 2020/10/19 20:28
 * description:统一结果返回,接口的统一返回格式
 */
@Data
public class JsResult<T> {

    private Integer code;
    private String msg;
    private T data;


    public static  <T> JsResult setJs(int code,String msg,T data){
        JsResult jsResult = new JsResult();
        jsResult.setCode(code);
        jsResult.setMsg(msg);
        jsResult.setData(data);
        return jsResult;
    }

    // 成功
    public static JsResult ok(){
        return setJs(1,"OK",null);
    }

    // 成功
    public static <T> JsResult ok(T t){
        return setJs(1,"OK",t);
    }

    // 失败
    public static JsResult fail(){
        return setJs(2,"Fail",null);
    }

    // 失败
    public static <T> JsResult fail(T t){
        return setJs(2,"Fail",t);
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
