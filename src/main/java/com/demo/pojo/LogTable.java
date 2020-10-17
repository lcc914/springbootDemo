package com.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author lcc
 * @since 2020-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_table")
@ApiModel(value = "LogTable对象", description = "")
public class LogTable extends Model<LogTable> {

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
        return id;
    }

}
