package com.xxzy.EXLG.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * 
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
@Data
@TableName("User")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 别名
	 */
	private String nickName;
//	/**
//	 *  年龄
//	 */
//	private Integer age;
	/**
	 * 0: 女  1: 男
	 */
	private Integer sex;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像
	 */
	private String headImage;
	/**
	 * 0:普通用户   1:工作人员 
	 */
	private String userType;
	/**
	 * 员工id   默认-1: 表示普通用户
	 */
	private Long employeeId;
	/**
	 * 所属省份id
	 */
	private Long provinceId;
	/**
	 * 所属城市id
	 */
	private Long cityId;
	/**
	 * 所属县级id
	 */
	private Long countyId;
	/**
	 * 收货站点ID
	 */
	private Long stageId;
	/**
	 * 创建时间
	 * 自动填充
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 修改时间
	 * 自动填充
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	/**
	 * 0:未删除  1：删除
	 */
	private Integer isDelete;

}
