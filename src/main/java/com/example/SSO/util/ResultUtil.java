package com.example.SSO.util;

import com.example.SSO.constant.ResultEnum;
import com.example.SSO.domain.entity.Result;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:41
 */
public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setStatus(ResultEnum.SUCCESS.getStatus());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Object object) {
        Result result = new Result();
        result.setStatus(ResultEnum.ERROR.getStatus());
        result.setMsg(ResultEnum.ERROR.getMsg());
        result.setData(object);
        return result;
    }

    public static Result isNull(Object object) {
        Result result = new Result();
        result.setStatus(ResultEnum.ISNULL.getStatus());
        result.setMsg(ResultEnum.ISNULL.getMsg());
        result.setData(object);
        return result;
    }

    public static Result isExist() {
        Result result = new Result();
        result.setStatus(ResultEnum.ISEXIST.getStatus());
        result.setMsg(ResultEnum.ISEXIST.getMsg());
        return result;
    }

    public static Result notExist(Object object) {
        Result result = new Result();
        result.setStatus(ResultEnum.NOTEXIST.getStatus());
        result.setMsg(ResultEnum.NOTEXIST.getMsg());
        result.setData(object);
        return result;
    }
}
