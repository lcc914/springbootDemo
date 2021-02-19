package com.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_info")
@ApiModel(value = "LogInfo对象", description = "")
public class LogInfo extends Model<LogInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作类型")
    private String operateType;

    @ApiModelProperty(value = "操作时间")
    private Date operateDate;

    @ApiModelProperty(value = "操作结果")
    private String operateResult;

    @ApiModelProperty(value = "备注")
    private String remark;


    public static final String ID = "id";

    public static final String OPERATOR = "operator";

    public static final String OPERATE_TYPE = "operate_type";

    public static final String OPERATE_DATE = "operate_date";

    public static final String OPERATE_RESULT = "operate_result";

    public static final String REMARK = "remark";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
