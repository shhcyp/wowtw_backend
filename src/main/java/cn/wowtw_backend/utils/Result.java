package cn.wowtw_backend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public static Result success(Object data) {
        return new Result(1, "success", data);
    }

    public static Result success() {
        return new Result(1, "success", null);
    }

    public static Result success(String message) {
        return new Result(1, message, null);
    }

    public static Result fail() {
        return new Result(0, "fail", null);
    }

    public static Result fail(String message) {
        return new Result(0, message, null);
    }

    public static Result fail(String message, Object data) {
        return new Result(0, message, data);
    }
}
