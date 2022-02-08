package com.hsy.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 单点登录服务端信息
 * @author： husiyi
 * @date：   2021-11-09
 * @version： V1.0
 */
@ApiModel("单点登录服务端信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cas_service")
public class CasService implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * id
	*/
	@ApiModelProperty(value="id -必填", position=0)
	@TableId(type = IdType.UUID)
	private String id;

	/**
	 * 描述
	*/
	@ApiModelProperty(value="描述", position=1)
	private java.lang.String description;

	/**
	 * 排序
	*/
	@ApiModelProperty(value="排序 -必填", position=2)
	private java.lang.Integer evaluationOrder;

	/**
	 * 登出类型
	*/
	@ApiModelProperty(value="登出类型", position=3)
	private java.lang.Integer logoutType;

	/**
	 * 登出URL
	*/
	@ApiModelProperty(value="登出URL", position=4)
	private java.lang.String logoutUrl;

	/**
	 * 服务名称
	*/
	@ApiModelProperty(value="服务名称 -必填", position=5)
	private java.lang.String name;

	/**
	 * 服务id
	*/
	@ApiModelProperty(value="服务id -必填", position=6)
	private java.lang.String serviceid;


	/**
	 * 状态(1 开启 0 关闭)
	 */
	@ApiModelProperty(value="状态(1 开启 0 关闭)", position=6)
	private java.lang.String status;
}
