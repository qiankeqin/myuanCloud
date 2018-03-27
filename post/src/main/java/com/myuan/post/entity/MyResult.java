package com.myuan.post.entity;

import lombok.Data;

/*
 * @author liuwei
 * @date 2018/1/20 8:35
 * 结果类
 */
@Data
public class MyResult {

    //-1 失败 、 1 成功 、0 无权限
    private String status;
    private String msg;
    private Object data;

    public MyResult(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static MyResult ok(String msg) {
        return new MyResult("1", msg, null);
    }

    public static MyResult data(Object data) {
        return new MyResult("1", null, data);
    }

    public static MyResult error(String msg) {
        return new MyResult("-1", msg, null);
    }
}
