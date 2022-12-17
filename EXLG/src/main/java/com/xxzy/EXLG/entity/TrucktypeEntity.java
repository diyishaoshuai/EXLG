package com.xxzy.EXLG.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("TruckType")
public class TrucktypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 类型名
	 */
	private String typeName;
	/**
	 * 表示车辆用途
		0: 子公司之间送货
		1: 配送点到子公司
	 */
	private Integer purpose;
	/**
	 * 该类型所有车的总数
	 */
	private Integer count;
	/**
	 * 该分类下剩余车辆
	 */
	private Integer surplus;
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
