package com.demo.pojo.vo;

import com.demo.emuns.ResultEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: LiChangChen
 * @Date: 2020/10/13
 */

@Data
public class Result {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    /**
     * 构造方法私有化,里面的方法都是静态方法
     * 达到保护属性的作用
     */
    private Result() {

    }

    /**
     * 这里是使用链式编程
     */
    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultEnum.HTTP_ERROR_100.getCode());
        result.setMessage(ResultEnum.HTTP_ERROR_100.getMessage());
        return result;
    }

}