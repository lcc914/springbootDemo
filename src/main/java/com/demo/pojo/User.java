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
 * 用户
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@ApiModel(value = "User对象", description = "用户")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户状态:0=正常,1=禁用")
    private Boolean state;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像图片地址")
    private String headImgUrl;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "密码加盐")
    private String salt;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "修改时间")
    private Date editTime;

    @ApiModelProperty(value = "修改人")
    private String editor;

    @ApiModelProperty(value = "逻辑删除:0=未删除,1=已删除")
    private Boolean deleted;

    public static final String ID = "id";

    public static final String STATE = "state";

    public static final String NAME = "name";

    public static final String HEAD_IMG_URL = "head_img_url";

    public static final String MOBILE = "mobile";

    public static final String SALT = "salt";

    public static final String PASSWORD = "password";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATOR = "creator";

    public static final String EDIT_TIME = "edit_time";

    public static final String EDITOR = "editor";

    public static final String DELETED = "deleted";


    @Override
    protected Serializable pkVal() {
        return id;
    }
}
