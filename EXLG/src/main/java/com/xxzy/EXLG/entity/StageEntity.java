package com.xxzy.EXLG.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Stage")
public class StageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 站点名
	 */
	private String name;
	/**
	 * 所属子公司id
	 */
	private Long sqId;
	/**
	 * 站点详细地址
	 */
	private String location;
	/**
	 * 站点联系电话
	 */
	private String telephone;
	/**
	 * 站点邮箱
	 */
	private String email;
	/**
	 * 该配送点起步价
	 */
	private BigDecimal startingFare;
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
