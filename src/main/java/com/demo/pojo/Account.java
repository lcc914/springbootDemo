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
 * 账号
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("account")
@ApiModel(value = "Account对象", description = "账号")
public class Account extends Model<Account> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "登录账号,如手机号等")
    private String openCode;

    @ApiModelProperty(value = "账号类别")
    private Boolean category;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "修改时间")
    private Date editTime;

    @ApiModelProperty(value = "修改人")
    private String editor;

    @ApiModelProperty(value = "逻辑删除:0=未删除,1=已删除")
    private Double deleted;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String OPEN_CODE = "open_code";

    public static final String CATEGORY = "category";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATOR = "creator";

    public static final String EDIT_TIME = "edit_time";

    public static final String EDITOR = "editor";

    public static final String DELETED = "deleted";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
