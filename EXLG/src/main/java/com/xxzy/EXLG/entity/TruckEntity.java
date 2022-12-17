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
@TableName("Truck")
public class TruckEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 分类id
	 */
	private Long typeId;
	/**
	 * 车牌号
	 */
	private String truckMark;
	/**
	 * 0:待机 1:运输	
	 */
	private Integer status;
	/**
	 * 所属id(站点/子公司)
	 */
	private Long affiliationId;
	/**
	 * 员工id(司机)
	 */
	private Long employeeId;
	/**
	 * 最大承受重量
	 */
	private Double maxWeight;
	/**
	 * 剩余重量
	 */
	private Double remainderWeight;
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
