package cn.edu.tsinghua.thutickets.common;

import lombok.Data;

@Data
public class Result {
    private Integer status;
    private String msg;
    private Object data;

    public Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static Result build(Integer status, String msg, Object data) {
        return new Result(status, msg, data);
    }

    public static Result buildOK(Object data) {
        return new Result(200, "OK", data);
    }
}

