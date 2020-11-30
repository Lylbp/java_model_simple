package com.lylbp.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lylbp.core.entity.ResResult;
import com.lylbp.core.entity.DataPage;
import com.lylbp.core.entity.PageResResult;
import com.lylbp.common.enums.ResResultEnum;

/**
 * 结果集工具类
 *
 * @author weiwenbin
 */
public class ResResultUtil {
    private ResResultUtil() {
    }

    public static <T> ResResult<T> makeRsp(int code, String msg) {
        return new ResResult<T>().setCode(code).setMsg(msg);
    }

    public static <T> ResResult<T> makeRsp(int code, String msg, T data) {
        return new ResResult<T>().setCode(code).setMsg(msg).setData(data);
    }


    public static <T> ResResult<T> makeRsp(ResResultEnum responseEnum) {
        return new ResResult<T>().setCode(responseEnum.getCode()).setMsg(responseEnum.getMsg());
    }

    public static <T> ResResult<T> makeRsp(ResResultEnum responseEnum, T data) {
        return new ResResult<T>().setCode(responseEnum.getCode()).setMsg(responseEnum.getMsg()).setData(data);
    }


    public static <T> ResResult<T> success() {
        return new ResResult<T>().setCode(ResResultEnum.SUCCESS.getCode()).setMsg(ResResultEnum.SUCCESS.getMsg());
    }

    public static <T> ResResult<T> success(T data) {
        return new ResResult<T>().setCode(ResResultEnum.SUCCESS.getCode()).setMsg(ResResultEnum.SUCCESS.getMsg()).setData(data);
    }


    public static <T> ResResult<T> error() {
        return new ResResult<T>().setCode(ResResultEnum.SYSTEM_ERR.getCode()).setMsg(ResResultEnum.SYSTEM_ERR.getMsg());
    }

    public static <T> ResResult<T> error(ResResultEnum responseEnum) {
        return new ResResult<T>().setCode(responseEnum.getCode()).setMsg(responseEnum.getMsg());
    }

    public static <T> ResResult<T> error(T data) {
        return new ResResult<T>().setCode(ResResultEnum.SYSTEM_ERR.getCode()).setMsg(ResResultEnum.SYSTEM_ERR.getMsg()).setData(data);
    }


    public static <T> ResResult<PageResResult<T>> makePageRsp(Page<T> page) {
        return makePageRsp(new PageResResult<T>(page));
    }

    public static <T> ResResult<PageResResult<T>> makePageRsp(DataPage<T> dataPage) {
        return makePageRsp( new PageResResult<T>(dataPage));
    }

    public static <T> ResResult<PageResResult<T>> makePageRsp(PageResResult<T> pageResult) {
        return new ResResult<PageResResult<T>>().setCode(ResResultEnum.SUCCESS.getCode()).setMsg(ResResultEnum.SUCCESS.getMsg()).setData(pageResult);
    }
}
