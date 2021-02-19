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
 * 权限
 * </p>
 *
 * @author lcc
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("permission")
@ApiModel(value = "Permission对象", description = "权限")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属父级权限ID")
    private Long parentId;

    @ApiModelProperty(value = "权限唯一CODE代码")
    private String code;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限介绍")
    private String intro;

    @ApiModelProperty(value = "权限类别")
    private Boolean category;

    @ApiModelProperty(value = "URL规则")
    private Long uri;

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

    public static final String PARENT_ID = "parent_id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String INTRO = "intro";

    public static final String CATEGORY = "category";

    public static final String URI = "uri";

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
